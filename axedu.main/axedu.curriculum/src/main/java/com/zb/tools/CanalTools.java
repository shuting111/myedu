package com.zb.tools;

import com.alibaba.fastjson.JSON;
import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.common.utils.AddressUtils;
import com.alibaba.otter.canal.protocol.CanalEntry.*;
import com.alibaba.otter.canal.protocol.Message;
import com.zb.mapper.AreaMapper;
import com.zb.mapper.GradeMapper;
import com.zb.mapper.SubjectMapper;
import com.zb.pojo.Area;
import com.zb.pojo.Grade;
import com.zb.pojo.Subject;
import com.zb.util.RedisUtil;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.util.*;

@Component
public class CanalTools {

    public void execution() {
        // 创建链接
        CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress(AddressUtils.getHostIp(),
                11111), "example", "root", "ok");
        int batchSize = 1000;
        int emptyCount = 0;
        try {
            connector.connect();
            connector.subscribe(".*\\..*");
            connector.rollback();
            int totalEmtryCount = 1200;
            while (emptyCount < totalEmtryCount) {
                Message message = connector.getWithoutAck(batchSize); // 获取指定数量的数据
                long batchId = message.getId();
                int size = message.getEntries().size();
                if (batchId == -1 || size == 0) {
                    emptyCount++;
//                    System.out.println("empty count : " + emptyCount);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    emptyCount = 0;
                    // System.out.printf("message[batchId=%s,size=%s] \n", batchId, size);
                    printEntry(message.getEntries());
                }

                connector.ack(batchId); // 提交确认
                // connector.rollback(batchId); // 处理失败, 回滚数据
            }

            System.out.println("empty too many times, exit");
        } finally {
            connector.disconnect();
        }
    }

    private void printEntry(List<Entry> entrys) {
        for (Entry entry : entrys) {
            if (entry.getEntryType() == EntryType.TRANSACTIONBEGIN || entry.getEntryType() == EntryType.TRANSACTIONEND) {
                continue;
            }

            RowChange rowChage = null;
            try {
                rowChage = RowChange.parseFrom(entry.getStoreValue());
            } catch (Exception e) {
                throw new RuntimeException("ERROR ## parser of eromanga-event has an error , data:" + entry.toString(),
                        e);
            }

            EventType eventType = rowChage.getEventType();
            System.out.println(String.format("================> binlog[%s:%s] , name[%s,%s] , eventType : %s",
                    entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(),
                    entry.getHeader().getSchemaName(), entry.getHeader().getTableName(),
                    eventType));

            for (RowData rowData : rowChage.getRowDatasList()) {
                if (eventType == EventType.DELETE) {
                    //调用的删除的方法
                    if(entry.getHeader().getSchemaName().equals("ax_eud")&&entry.getHeader().getTableName().equals("curriculum")){
                        printColumn(rowData.getBeforeColumnsList());
                    }else if(entry.getHeader().getSchemaName().equals("ax_eud")&&entry.getHeader().getTableName().equals("ad_catefory")){
                        updateRedisData(rowData.getBeforeColumnsList());
                    }else if(entry.getHeader().getSchemaName().equals("ax_eud")&&entry.getHeader().getTableName().equals("grade")){
                        updateGrade(rowData.getBeforeColumnsList());
                    }else if(entry.getHeader().getSchemaName().equals("ax_eud")&&entry.getHeader().getTableName().equals("grade_subject")){
                        listenerSubLinkGrade(rowData.getBeforeColumnsList());
                    }else if(entry.getHeader().getSchemaName().equals("ax_eud")&&entry.getHeader().getTableName().equals("area")){
                        updateArea();
                    }

                } else if (eventType == EventType.INSERT) {
                    //调用的添加的方法
                    if(entry.getHeader().getSchemaName().equals("ax_eud")&&entry.getHeader().getTableName().equals("curriculum")){
                        addESData(rowData.getAfterColumnsList());
                    }else if(entry.getHeader().getSchemaName().equals("ax_eud")&&entry.getHeader().getTableName().equals("ad_catefory")){
                        updateRedisData(rowData.getAfterColumnsList());
                    }else if(entry.getHeader().getSchemaName().equals("ax_eud")&&entry.getHeader().getTableName().equals("grade")){
                        updateGrade(rowData.getAfterColumnsList());
                    }else if(entry.getHeader().getSchemaName().equals("ax_eud")&&entry.getHeader().getTableName().equals("grade_subject")){
                        listenerSubLinkGrade(rowData.getAfterColumnsList());
                    }else if(entry.getHeader().getSchemaName().equals("ax_eud")&&entry.getHeader().getTableName().equals("area")){
                        updateArea();
                    }

                } else {
                    //调用的修改的方法
                    if(entry.getHeader().getSchemaName().equals("ax_eud")&&entry.getHeader().getTableName().equals("curriculum")){
                        updateESData(rowData.getAfterColumnsList());
                    }else if(entry.getHeader().getSchemaName().equals("ax_eud")&&entry.getHeader().getTableName().equals("ad_catefory")){
                        updateRedisData(rowData.getAfterColumnsList());
                    }else if(entry.getHeader().getSchemaName().equals("ax_eud")&&entry.getHeader().getTableName().equals("grade")){
                        updateGrade(rowData.getAfterColumnsList());
                    }else if(entry.getHeader().getSchemaName().equals("ax_eud")&&entry.getHeader().getTableName().equals("grade_subject")){
                        listenerSubLinkGrade(rowData.getAfterColumnsList());
                    }else if(entry.getHeader().getSchemaName().equals("ax_eud")&&entry.getHeader().getTableName().equals("area")){
                        updateArea();
                    }

                }
            }
        }
    }

    @Autowired
    private RestHighLevelClient client;
    @Autowired
    private DiscoveryClient discoveryClient;
    //修改调用的方法
    private void updateESData(List<Column> columns) {
        System.out.println("同步修改数据");
        try {
            Map<String, Object> data = new HashMap<>();
            String id ="";
            for (Column column : columns) {
                if(column.getName().equals("id")){
                    id = column.getValue()+"";
                    continue;
                }
                data.put(column.getName(),column.getValue() );
            }
            System.out.println(id);
            UpdateRequest updateRequest = new UpdateRequest("my_curriculum", "doc", id);
            updateRequest.doc(data);
            UpdateResponse updateResponse = client.update(updateRequest);
            DocWriteResponse.Result result = updateResponse.getResult();
            System.out.println(result);

        } catch (Exception e) {
            e.printStackTrace();
        }
        //同步redis
        String myurl ="http://localhost:9001/CurriculmToRedis";
        restTemplate.getForObject(myurl, String.class);
    }
    //添加调用的方法
    private void addESData(List<Column> columns) {
        System.out.println("同步添加数据");

        try {
            Map<String, Object> data = new HashMap<>();
            String id ="";
            for (Column column : columns) {
                if(column.getName().equals("id")){
                    id = column.getValue()+"";
                    continue;
                }
                data.put(column.getName(),column.getValue() );
            }
            System.out.println(id);
            //创建请求对象
            IndexRequest indexRequest = new IndexRequest("my_curriculum", "doc", id);
            //绑定数据
            indexRequest.source(data);
            //执行获取响应
            IndexResponse indexResponse = client.index(indexRequest);
            DocWriteResponse.Result result = indexResponse.getResult();
            System.out.println(result);

            //同步redis
            String myurl ="http://localhost:9001/CurriculmToRedis";
            restTemplate.getForObject(myurl, String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //删除调用的方法
    private void printColumn(List<Column> columns) {
        System.out.println("同步删除数据");

        try {
            Map<String, Object> data = new HashMap<>();
            String id ="";
            for (Column column : columns) {
                if(column.getName().equals("id")){
                    id = column.getValue()+"";
                    continue;
                }
            }
            System.out.println(id);
            DeleteRequest deleteRequest = new DeleteRequest("my_curriculum", "doc", id);
            DeleteResponse deleteResponse = client.delete(deleteRequest);
            DocWriteResponse.Result result = deleteResponse.getResult();
            System.out.println(result.name());

            //同步redis
            String myurl ="http://localhost:9001/CurriculmToRedis";
            restTemplate.getForObject(myurl, String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Autowired
    private RestTemplate restTemplate;

    private void updateRedisData(List<Column> columns) {
        Set<Integer> categoryId = new HashSet<>();
        for (Column column : columns) {
            System.out.println(column.getName() + " : " + column.getValue() + "  update=" + column.getUpdated());
            if(column.getName().equals("category_id")) {
                categoryId.add(Integer.parseInt(column.getValue()));
            }
        }
        for (Integer cid : categoryId) {
            //程序内部发起的http请求， 调用nginx的执行写的操作的接口
            String myurl ="http://localhost:9000/axedu?id="+cid;
            String result = restTemplate.getForObject(myurl, String.class);
            System.out.println("远程调用nginx中的接口程序:"+result);
        }
    }
    //年级表发生变化时，同步redis里的年级信息
    @Autowired(required = false)
    private GradeMapper gradeMapper;
    @Autowired(required = false)
    private RedisUtil redisUtil;
    @Autowired(required = false)
    private SubjectMapper subjectMapper;
    public void updateGrade(List<Column> columns){
        for (Column column : columns) {
            if(column.getName().equals("level")){
                Object value  = column.getValue();
                this.gradeToRedis(value);
            }
        }
    }
    //年级和科目联系表发生变化时，同步redis里的年级科目信息
    public void listenerSubLinkGrade(List<Column> columns){
        for (Column column : columns) {
            if(column.getName().equals("gradeid")){
                try {
                    Grade grade = gradeMapper.getGradeById(Long.parseLong(column.getValue().toString()));
                    this.gradeToRedis(grade.getLevel());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //上面方法中提取的公共代码
    public void gradeToRedis(Object value){
        Map<String , Object> map = new HashMap<>();
        map.put("level",value);
        List<Grade> list = null;
        String key = "grade"+value;
        try {
            //查询年级的集合
            list= gradeMapper.getGradeListByMap(map);
            for (Grade g : list) {
                //查询每个年级对应的科目的集合，并封装
                List<Subject> sub = subjectMapper.findSubjectByGradeId(g.getGradeid());
                g.setSubjects(sub);
            }
            //存入redis
            redisUtil.set(key,JSON.toJSONString(list));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //同步redis里的地区信息
    @Autowired(required = false)
    private AreaMapper areaMapper;
    public void updateArea(){
        String key = "area";
        List<Area> list = null;
        Map<String , Object> map = new HashMap<>();
        map.put("level",1);
        try {
            list = areaMapper.getAreaListByMap(map);
            for (Area area : list) {
                Map<String,Object> param = new HashMap<>();
                param.put("parent",area.getId());
                List<Area> child = areaMapper.getAreaListByMap(param);
                for (Area a : child) {
                    Map<String,Object> param1 = new HashMap<>();
                    param1.put("parent",a.getId());
                    List<Area> child1 = areaMapper.getAreaListByMap(param1);
                    a.setChild(child1);
                }
                area.setChild(child);
            }
            redisUtil.set(key,JSON.toJSONString(list));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
