package com.zb.service.impl;

import com.alibaba.fastjson.JSON;
import com.zb.mapper.SubjectMapper;
import com.zb.pojo.Subject;
import com.zb.service.SubjectService;
import com.zb.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @author 王淑婷
 * @Description TODO
 * @Date 2020/6/16
 * @Version V1.0
 */
@Service
public class SubjectServiceImpl implements SubjectService {
    @Autowired(required = false)
    private SubjectMapper subjectMapper;



    @Override
    public List<Subject> findSubject() {
        try {
            return subjectMapper.getSubjectListByMap(new HashMap<>());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
