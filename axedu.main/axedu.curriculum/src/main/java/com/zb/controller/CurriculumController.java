package com.zb.controller;

import com.zb.form.CurriArgs;
import com.zb.pojo.Advert;
import com.zb.pojo.Curriculum;
import com.zb.service.CurriculumService;
import com.zb.util.PageUtil;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 王淑婷
 * @Description TODO
 * @Date 2020/6/8
 * @Version V1.0
 */
@RestController
@CrossOrigin
public class CurriculumController {
    @Autowired
    private CurriculumService curriculumService;
    @Autowired
    private RestHighLevelClient client;

    //用户点击课程增加点击量
    @GetMapping("/IncreaseHits/{id}")
    public void IncreaseHits(@PathVariable("id") Integer id){
        curriculumService.IncreaseHits(id);
    }
    //将课程存入redis
    @GetMapping("/CurriculmToRedis")
    public void CurriculmToRedis(){
        curriculumService.CurriculmToRedis();
    }

    //前台显示猜你喜欢
    @GetMapping("/findLike")
    public List<Curriculum> findLike(){
        return curriculumService.findLike();
    }
    //精选课程
    @GetMapping("/findIsChoiceness/{isChoiceness}")
    public List<Curriculum> findIsChoiceness(@PathVariable("isChoiceness") Integer isChoiceness){
        return curriculumService.findIsChoiceness(isChoiceness);
    }

    //唯一查询,用于页面静态化
    @GetMapping("/findCurriculumById")
    public Curriculum findCurriculumById(Integer id){
        return curriculumService.findCurriculumById(id);
    }
    //维一查询，用于提供feign接口
    @GetMapping("/findCurriById/{id}")
    public Curriculum findCurriById(@PathVariable("id") Integer id){
        return curriculumService.findCurriculumById(id);
    }

    //创建es的映射
    @GetMapping("/addIndex")
    public void addIndex() throws Exception {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("my_curriculum");
        //参数
        createIndexRequest.settings(
                Settings.builder().put("number_of_shards", 1).
                        put("number_of_replicas", 0).
                        build());
        //创建映射
        createIndexRequest.mapping("doc", "{\n" +
                "\t\"properties\": {\n" +
                "\t\t\"className\": {\n" +
                "\t\t\t\"type\": \"text\",\n" +
                "\t\t\t\"analyzer\": \"ik_max_word\",\n" +
                "\t\t\t\"search_analyzer\": \"ik_smart\"\n" +
                "\t\t},\n" +
                "\t\t\"gradeName\": {\n" +
                "\t\t\t\"type\": \"text\",\n" +
                "\t\t\t\"analyzer\": \"ik_max_word\",\n" +
                "\t\t\t\"search_analyzer\": \"ik_smart\"\n" +
                "\t\t},\n" +
                "\t\t\"subName\": {\n" +
                "\t\t\t\"type\": \"text\",\n" +
                "\t\t\t\"analyzer\": \"ik_max_word\",\n" +
                "\t\t\t\"search_analyzer\": \"ik_smart\"\n" +
                "\t\t},\n" +
                "\t\t\"hits\": {\n" +
                "\t\t\t\"type\": \"integer\"\n" +
                "\t\t},\n" +
                "\t\t\"presentPrice\": {\n" +
                "\t\t\t\"type\": \"float\"\n" +
                "\t\t},\n" +
                "\n" +
                "\t\t\"score\": {\n" +
                "\t\t\t\"type\": \"float\"\n" +
                "\t\t},\n" +
                "\t\t\"classtime\": {\n" +
                "\t\t\t\"type\": \"keyword\"\n" +
                "\t\t},\n" +
                "\t\t\"teachingMethod\": {\n" +
                "\t\t\t\"type\": \"keyword\"\n" +
                "\t\t},\n" +
                "\t\t\"subjectId\": {\n" +
                "\t\t\t\"type\": \"keyword\"\n" +
                "\t\t},\n" +
                "\t\t\"gradeId\": {\n" +
                "\t\t\t\"type\": \"keyword\"\n" +
                "\t\t},\n" +
                "\n" +
                "\t\t\"areaid\": {\n" +
                "\t\t\t\"type\": \"keyword\"\n" +
                "\t\t},\n" +
                "\t\t\"createtime\": {\n" +
                "\t\t\t\"type\": \"date\",\n" +
                "\t\t\t\"format\": \"yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis\"\n" +
                "\t\t}\n" +
                "\t}\n" +
                "}", XContentType.JSON);
        //获取索引对象
        IndicesClient indices = client.indices();
        //创建并返回一个响应对象
        CreateIndexResponse indexResponse = indices.create(createIndexRequest);
        //是否执行成功
        boolean val = indexResponse.isAcknowledged();
        System.out.println(val);
    }

    //创建文档
    @GetMapping("/importData")
    public void importData()throws Exception{
        List<Curriculum> list = curriculumService.findCurricilumAll();
        for (Curriculum c : list) {
            Map<String,Object> data =new HashMap<>();
            data.put("className", c.getClassName());
            data.put("subjectId",c.getSubjectId());
            data.put("subName",c.getSubName());
            data.put("gradeId",c.getGradeId());
            data.put("gradeName",c.getGradeName());
            data.put("classtime",c.getClasstime());
            data.put("teachingMethod",c.getTeachingMethod());
            data.put("schoolId",c.getSchoolId());
            data.put("originalPrice",c.getOriginalPrice());
            data.put("presentPrice",c.getPresentPrice());
            data.put("banrong",c.getBanrong());
            data.put("openingRule",c.getOpeningRule());
            data.put("classIntroduce",c.getClassIntroduce());
            data.put("teachingFeatures",c.getTeachingFeatures());
            data.put("teachingContent",c.getTeachingContent());
            data.put("isChoiceness",c.getIsChoiceness());
            data.put("detailsimg",c.getDetailsimg());
            data.put("pageimg",c.getPageimg());
            data.put("areaid",c.getAreaid());
            data.put("createtime",c.getCreatetime());
            data.put("hits",c.getHits());
            data.put("like",c.getLike());
            data.put("classnum",c.getClassnum());
            data.put("score",c.getScore());

            //创建请求对象
            IndexRequest indexRequest = new IndexRequest("my_curriculum", "doc", c.getId() + "");
            //绑定数据
            indexRequest.source(data);
            //执行获取响应
            IndexResponse indexResponse = client.index(indexRequest);
            DocWriteResponse.Result result = indexResponse.getResult();
            System.out.println(result);
        }


    }

    //es关键字查询  CurriArgs是封装的参数类
    @PostMapping("/findKeyWord")
    public PageUtil<Curriculum> findKeyWord(CurriArgs c) {
        try {
            return curriculumService.findKeyWord(c);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    //向后台管理模块提供的添加课程的接口
    @PostMapping("/insertCurriculum")
    public int insertCurriculum(@Valid Curriculum curriculum){
        return curriculumService.insertCurriculum(curriculum);
    }

    //向订单模块提供特价课程的接口
    @GetMapping("/findIsDiscount")
    public List<Curriculum> findIsDiscount(){
        return curriculumService.findIsDiscount();
    }


}
