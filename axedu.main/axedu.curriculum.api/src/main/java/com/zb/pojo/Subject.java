package com.zb.pojo;
import java.io.Serializable;

/***
*   
*/
public class Subject implements Serializable {
    //
    private Integer subjectId;
    //
    private String subjectName;
    //get set 方法
    public void setSubjectId (Integer  subjectId){
        this.subjectId=subjectId;
    }
    public  Integer getSubjectId(){
        return this.subjectId;
    }
    public void setSubjectName (String  subjectName){
        this.subjectName=subjectName;
    }
    public  String getSubjectName(){
        return this.subjectName;
    }
}
