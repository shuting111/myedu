package com.zb.pojo;
import java.io.Serializable;

/***
*   
*/
public class GradeSubject implements Serializable {
    //年级和科目关系表的id
    private Integer gsid;
    //年级id
    private Integer gradeid;
    //科目id
    private Integer subjectid;
    //get set 方法
    public void setGsid (Integer  gsid){
        this.gsid=gsid;
    }
    public  Integer getGsid(){
        return this.gsid;
    }
    public void setGradeid (Integer  gradeid){
        this.gradeid=gradeid;
    }
    public  Integer getGradeid(){
        return this.gradeid;
    }
    public void setSubjectid (Integer  subjectid){
        this.subjectid=subjectid;
    }
    public  Integer getSubjectid(){
        return this.subjectid;
    }
}
