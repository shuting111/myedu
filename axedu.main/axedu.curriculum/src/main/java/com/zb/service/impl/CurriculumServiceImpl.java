package com.zb.service.impl;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.zb.config.RabbitConfig;
import com.zb.form.CurriArgs;
import com.zb.mapper.CurriculumMapper;
import com.zb.mapper.GradeMapper;
import com.zb.mapper.SubjectMapper;

import com.zb.pojo.Curriculum;
import com.zb.pojo.Grade;
import com.zb.pojo.Subject;
import com.zb.service.CurriculumService;
import com.zb.util.PageUtil;
import com.zb.util.RedisUtil;
import com.zb.util.SortList;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;

import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.index.query.Operator;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 王淑婷
 * @Description TODO
 * @Date 2020/6/8
 * @Version V1.0
 */
@Service
public class CurriculumServiceImpl implements CurriculumService {
    @Autowired(required = false)
    private CurriculumMapper curriculumMapper;
    @Autowired
    private RedisUtil redisUtils;
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private RestHighLevelClient client;

    @Autowired(required = false)
    private GradeMapper gradeMapper;
    @Autowired(required = false)
    private SubjectMapper subjectMapper;



    @Override
    public void CurriculmToRedis() {
        Map<String, Object> map = new HashMap<>();
        try {
            List<Curriculum> curriculumList = curriculumMapper.getCurriculumListByMap(map);
            redisUtils.set("curriculum", JSON.toJSONString(curriculumList));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //增加点击量，将请求压入mq
    @Override
    public void IncreaseHits(Integer id) {
        Map<String,Object> param = new HashMap<>();
        param.put("curriculumId",id);
        amqpTemplate.convertAndSend(RabbitConfig.EXCHANGE_TOPIC_INFORM,"inform.curriculum",param);
    }

    @Override
    public List<Curriculum> findLike() {
        Map<String,Object> param = new HashMap<>();
        param.put("like",1);
        List<Curriculum> list = null;
        String key = "likeCurriculum";
        if(redisUtils.hasKey(key)){
            System.out.println("查询redis");
            String jsonobj = redisUtils.get(key).toString();
            list = JSON.parseArray(jsonobj, Curriculum.class);
        }else {
            System.out.println("从数据库中查询");
            try {
                list = curriculumMapper.getCurriculumListByMap(param);
                redisUtils.set(key,JSON.toJSONString(list),24*60*60);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    @Cacheable(value = "cache",key = "'curr'+#id")
    public Curriculum findCurriculumById(Integer id) {
        System.out.println("encache中没有数据");
        Curriculum curriculum = null;
        String key = "curr"+id;
        if(redisUtils.hasKey(key)){
            System.out.println("查询redis");
            String jsonstr = redisUtils.get(key).toString();
            curriculum = JSON.parseObject(jsonstr,Curriculum.class);
        }else {
            try {
                System.out.println("从数据库中查询");
                curriculum = curriculumMapper.getCurriculumById(Long.parseLong(id + ""));
                Grade grade = gradeMapper.getGradeById(Long.parseLong(curriculum.getGradeId()+""));
                curriculum.setGradeName(grade.getGradename());
                Subject subject = subjectMapper.getSubjectById(Long.parseLong(curriculum.getSubjectId()+""));
                curriculum.setSubName(subject.getSubjectName());
                redisUtils.set(key,JSON.toJSONString(curriculum),24*60*60);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return curriculum;
    }

    @Override
    public List<Curriculum> findIsChoiceness(Integer isChoiceness) {
        Map<String,Object> param = new HashMap<>();
        param.put("isChoiceness",isChoiceness);
        List<Curriculum> list = null;
        try {
            list=curriculumMapper.getCurriculumListByMap(param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Curriculum> findCurricilumAll() {
        Map<String, Object> map = new HashMap<>();
        try {
            return curriculumMapper.getCurriculumListByMap(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public PageUtil<Curriculum> findKeyWord(CurriArgs c) throws Exception{
        PageUtil<Curriculum> page = new PageUtil<>();
        List<Curriculum> list = new ArrayList<>();
        //创建查询请求对象
        SearchRequest searchRequest = new SearchRequest("my_curriculum");
        searchRequest.types("doc");
        //构建查询方式
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.from(( c.getIndex()- 1) * c.getSize());
        searchSourceBuilder.size(c.getSize());
        //因为是多个条件的组合创建bool查询
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //关键字查询
        if(c.getKeyword()!=null&&!"".equals(c.getKeyword())){
            ///多个列的分词查询
            boolQueryBuilder.must(QueryBuilders.multiMatchQuery(c.getKeyword(), new String[]{"className", "gradeName","subName"})
                    .operator(Operator.OR).field("className", 10));
        }
        if (c.getGradeId()!=null&&c.getGradeId()!=-1){
            boolQueryBuilder.filter((QueryBuilders.termQuery("gradeId",c.getGradeId())));
        }
        if (c.getSubjectId()!=null&&c.getSubjectId()!=-1){
            boolQueryBuilder.filter((QueryBuilders.termQuery("subjectId",c.getSubjectId())));
        }
        if (c.getTeachingMethod()!=null&&c.getTeachingMethod()!=-1){
            boolQueryBuilder.filter((QueryBuilders.termQuery("teachingMethod",c.getTeachingMethod())));
        }
        if (c.getAreaid()!=null&&c.getAreaid()!=-1){
            boolQueryBuilder.filter((QueryBuilders.termQuery("areaid",c.getAreaid())));
        }
        if(c.getSort()!=null&&c.getSort()!=-1){
            if(c.getSort()==1){
                searchSourceBuilder.sort(new FieldSortBuilder("score").order(SortOrder.DESC));
            }
            if(c.getSort()==2){
                searchSourceBuilder.sort(new FieldSortBuilder("hits").order(SortOrder.DESC));
            }
            if(c.getSort()==3){
                searchSourceBuilder.sort(new FieldSortBuilder("createtime").order(SortOrder.DESC));
            }
            if(c.getSort()==4){
                searchSourceBuilder.sort(new FieldSortBuilder("presentPrice").order(SortOrder.ASC));
            }
        }
        //创建高亮对象
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        //设置标签
        highlightBuilder.preTags("<div style='color:red;'>");
        highlightBuilder.postTags("</div>");
        //添加高亮字段
        highlightBuilder.fields().add(new HighlightBuilder.Field("className"));
        highlightBuilder.fields().add(new HighlightBuilder.Field("subName"));
        highlightBuilder.fields().add(new HighlightBuilder.Field("gradeName"));

        //将highlightBuilder对象添加到查询对象中
        searchSourceBuilder.highlighter(highlightBuilder);
        //绑定查询构建对象
        searchSourceBuilder.query(boolQueryBuilder);
        //将构建对象存储到request对象中去
        searchRequest.source(searchSourceBuilder);
        //执行查询并获取响应对象
        SearchResponse searchResponse = client.search(searchRequest);
        //获取索引的hits
        SearchHits hitss = searchResponse.getHits();
        //获得查询的集合长度，对应分页工具类里的count
        long totalHits = hitss.getTotalHits();
        Integer count = Integer.parseInt(totalHits+"");
        //获取到数组对象(存储的数据)
        SearchHit[] hitsHits = hitss.getHits();
        for (SearchHit hit : hitsHits) {
            if(hit!=null){
                //获取普通数据
                Integer id = Integer.parseInt(hit.getId());
                Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                String className = sourceAsMap.get("className")==null?null:sourceAsMap.get("className").toString();
                Integer classtime =sourceAsMap.get("classtime")==null?null:Integer.parseInt(sourceAsMap.get("classtime").toString());
                Integer subjectId =sourceAsMap.get("subjectId")==null?null:Integer.parseInt(sourceAsMap.get("subjectId").toString());
                Integer teachingMethod =sourceAsMap.get("teachingMethod")==null?null:Integer.parseInt(sourceAsMap.get("teachingMethod").toString());
                Integer areaid =sourceAsMap.get("areaid")==null?null:Integer.parseInt(sourceAsMap.get("areaid").toString());
                Integer gradeId =sourceAsMap.get("gradeId")==null?null:Integer.parseInt(sourceAsMap.get("gradeId").toString());
                String pageimg = sourceAsMap.get("pageimg")==null?null:sourceAsMap.get("pageimg").toString();
                String createtime = sourceAsMap.get("createtime")==null?null:sourceAsMap.get("createtime").toString();
                Double presentPrice =sourceAsMap.get("presentPrice")==null?null:Double.parseDouble(sourceAsMap.get("presentPrice").toString());
                Double score =sourceAsMap.get("score")==null?null:Double.parseDouble(sourceAsMap.get("score").toString());
                Integer hits =sourceAsMap.get("hits")==null?null:Integer.parseInt(sourceAsMap.get("hits").toString());


                //获取高亮数据
                Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                if (highlightFields != null) {
                    HighlightField nameField = highlightFields.get("className");
                    //验证高亮部分中是否包含该属性
                    if (nameField != null) {
                        Text[] nameText = nameField.getFragments();
                        StringBuffer nameSbf = new StringBuffer();
                        for (Text text : nameText) {
                            nameSbf.append(text);
                        }
                        //覆盖掉原始的数据
                        className = nameSbf.toString();
                    }
                    HighlightField subNameField = highlightFields.get("subName");
                    //验证高亮部分中是否包含该属性
                    if (subNameField != null) {
                        Text[] subNameText = subNameField.getFragments();
                        StringBuffer subNameSbf = new StringBuffer();
                        for (Text text : subNameText) {
                            subNameSbf.append(text);
                        }
                        //覆盖掉原始的数据
                        className = subNameSbf.toString();
                    }
                    HighlightField gradeNameField = highlightFields.get("className");
                    //验证高亮部分中是否包含该属性
                    if (gradeNameField != null) {
                        Text[] gradeNameText = gradeNameField.getFragments();
                        StringBuffer gradeNameSbf = new StringBuffer();
                        for (Text text : gradeNameText) {
                            gradeNameSbf.append(text);
                        }
                        //覆盖掉原始的数据
                        className = gradeNameSbf.toString();
                    }
                }
                //封装到对象里
                Curriculum curriculum = new Curriculum();
               curriculum.setClassName(className);
               curriculum.setPresentPrice(presentPrice);
               curriculum.setPageimg(pageimg);
               curriculum.setSubjectId(subjectId);
               curriculum.setAreaid(areaid);
               curriculum.setHits(hits);
               curriculum.setGradeId(gradeId);
               curriculum.setClasstime(classtime);
               curriculum.setId(id);
               curriculum.setTeachingMethod(teachingMethod);
               curriculum.setScore(score);
               curriculum.setCreatetime(createtime);

                //添加到集合中
                list.add(curriculum);
            }
        }
        //封装到分页工具类中
        page.setSize(c.getSize());
        page.setCount(count);
        page.setData(list);
        page.setIndex(c.getIndex());
        return page;
    }

    @Override
    public int insertCurriculum(Curriculum curriculum) {
        try {
            return curriculumMapper.insertCurriculum(curriculum);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    //监听  增加点击量发起的队列
    @Override
    @RabbitListener(queues = RabbitConfig.QUEUE_room)
    public void reciveHits(Map<String,Object> param, Message message, Channel channel){
        if(param.size()>0){
            int id =(Integer) param.get("curriculumId");
            String curriculum = redisUtils.get("curriculum").toString();
            List<Curriculum> list = JSON.parseArray(curriculum,Curriculum.class);
            for (Curriculum c : list) {
                System.out.println(c.getId());
                if(c.getId() == id){
                    c.setHits(c.getHits()+1);
                    System.out.println(c.getHits());
                    break;
                }
            }
            redisUtils.set("curriculum", JSON.toJSONString(list));
        }
    }

    @Scheduled(cron = "00 21 22 * * ?")
    @Override
    public void guessLike(){
        String curriculum = redisUtils.get("curriculum").toString();
        List<Curriculum> list = JSON.parseArray(curriculum,Curriculum.class);
        SortList.sort(list, "hits", SortList.DESC);
        for (int i = 0;i<list.size();i++) {
            Curriculum c = list.get(i);
            if(i<5){
                c.setLike(1);
            }else {
                c.setLike(2);
            }
            try {
                curriculumMapper.updateCurriculum(c);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Curriculum> findIsDiscount() {
        return curriculumMapper.findIsDiscount();
    }


}
