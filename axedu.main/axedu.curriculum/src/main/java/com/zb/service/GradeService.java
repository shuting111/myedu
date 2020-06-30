package com.zb.service;

import com.zb.pojo.Grade;

import java.util.List;

/**
 * @author 王淑婷
 * @Description TODO
 * @Date 2020/6/10
 * @Version V1.0
 */
public interface GradeService {
    //根据父id查询年级的集合
    public List<Grade> findListByParentId(Integer pid);
    //查询全部年级
    public List<Grade> findGrade();

}
