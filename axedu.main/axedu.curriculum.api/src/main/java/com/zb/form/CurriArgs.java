package com.zb.form;

import java.io.Serializable;

/**
 * @author 王淑婷
 * @Description TODO
 * @Date 2020/6/11
 * @Version V1.0
 */
public class CurriArgs implements Serializable {
    //关键字
    private String keyword;
    //年级id
    private Integer gradeId;
    //科目id
    private Integer subjectId;
    //上课时间
    private Integer classtime;
    //授课方式
    private Integer teachingMethod;
    //上课地点
    private Integer areaid;
    //排序方式
    private Integer sort;
    //第几页
    private Integer index;
    //每页显示多少条
    private Integer size;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getGradeId() {
        return gradeId;
    }

    public void setGradeId(Integer gradeId) {
        this.gradeId = gradeId;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getClasstime() {
        return classtime;
    }

    public void setClasstime(Integer classtime) {
        this.classtime = classtime;
    }

    public Integer getTeachingMethod() {
        return teachingMethod;
    }

    public void setTeachingMethod(Integer teachingMethod) {
        this.teachingMethod = teachingMethod;
    }

    public Integer getAreaid() {
        return areaid;
    }

    public void setAreaid(Integer areaid) {
        this.areaid = areaid;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
