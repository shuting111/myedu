package com.zb.service;


import com.zb.pojo.Subject;

import java.util.List;

/**
 * @author 王淑婷
 * @Description TODO
 * @Date 2020/6/16
 * @Version V1.0
 */
public interface SubjectService {
    //查询全部课程
    public List<Subject> findSubject();
}
