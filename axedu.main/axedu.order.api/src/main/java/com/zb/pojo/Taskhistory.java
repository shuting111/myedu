package com.zb.pojo;
import java.io.Serializable;
import java.util.Date;
/***
*   
*/
public class Taskhistory implements Serializable {
    //任务id
    private Integer taskId;
    //创建时间
    private Date createTime;
    //交换机名称
    private String myExchange;
    //routingkey
    private String myRoutingKey;
    //任务内容
    private String requestBody;
    //乐观锁版本号
    private Integer version;
    //状态
    private Integer status;
    //get set 方法
    public void setTaskId (Integer  taskId){
        this.taskId=taskId;
    }
    public  Integer getTaskId(){
        return this.taskId;
    }
    public void setCreateTime (Date  createTime){
        this.createTime=createTime;
    }
    public  Date getCreateTime(){
        return this.createTime;
    }
    public void setMyExchange (String  myExchange){
        this.myExchange=myExchange;
    }
    public  String getMyExchange(){
        return this.myExchange;
    }
    public void setMyRoutingKey (String  myRoutingKey){
        this.myRoutingKey=myRoutingKey;
    }
    public  String getMyRoutingKey(){
        return this.myRoutingKey;
    }
    public void setRequestBody (String  requestBody){
        this.requestBody=requestBody;
    }
    public  String getRequestBody(){
        return this.requestBody;
    }
    public void setVersion (Integer  version){
        this.version=version;
    }
    public  Integer getVersion(){
        return this.version;
    }
    public void setStatus (Integer  status){
        this.status=status;
    }
    public  Integer getStatus(){
        return this.status;
    }
}
