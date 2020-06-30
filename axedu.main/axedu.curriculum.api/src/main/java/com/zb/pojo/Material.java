package com.zb.pojo;
import java.io.Serializable;

/***
*   
*/
public class Material implements Serializable {
    //课程资料id
    private Integer id;
    //年级id
    private Integer gradeid;
    //科目id
    private Integer subjectid;
    //创建时间
    private String createtime;

    //七牛云文件地址
    private String fileUrl;
    //get set 方法
    public void setId (Integer  id){
        this.id=id;
    }
    public  Integer getId(){
        return this.id;
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
    public void setCreatetime (String  createtime){
        this.createtime=createtime;
    }
    public  String getCreatetime(){
        return this.createtime;
    }

    public void setFileUrl (String  fileUrl){
        this.fileUrl=fileUrl;
    }
    public  String getFileUrl(){
        return this.fileUrl;
    }
}
