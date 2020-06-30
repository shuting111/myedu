package com.zb.service.impl;

import com.alibaba.fastjson.JSON;
import com.zb.mapper.GradeMapper;
import com.zb.mapper.SubjectMapper;
import com.zb.pojo.Grade;
import com.zb.pojo.Subject;
import com.zb.service.GradeService;
import com.zb.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 王淑婷
 * @Description TODO
 * @Date 2020/6/10
 * @Version V1.0
 */
@Service
public class GradeServiceImpl implements GradeService {
    @Autowired(required = false)
    private GradeMapper gradeMapper;
    @Autowired(required = false)
    private RedisUtil redisUtil;
    @Autowired(required = false)
    private SubjectMapper subjectMapper;

    @Override
    public List<Grade> findListByParentId(Integer pid) {
        Map<String , Object> map = new HashMap<>();
        map.put("level",pid);
        List<Grade> list = null;
        String key = "grade"+pid;
        if(redisUtil.hasKey(key)){
            System.out.println("从redis里查数据");
            String jsonobj = redisUtil.get(key).toString();
            list = JSON.parseArray(jsonobj,Grade.class);
        }else {
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
        return list;
    }

    @Override
    public List<Grade> findGrade() {
        return gradeMapper.findGrade();
    }
}
