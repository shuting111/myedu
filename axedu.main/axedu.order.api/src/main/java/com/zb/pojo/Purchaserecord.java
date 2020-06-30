package com.zb.pojo;
import java.io.Serializable;
import java.util.Date;
/***
*   
*/
public class Purchaserecord implements Serializable {
    //
    private Integer purchId;
    //课程编号
    private Integer goodsId;
    //用户编号
    private Integer userId;
    //课程价格
    private Integer price;
    //有效性：1：有效；2：无效
    private Integer valid;
    //开课时间
    private Date startTime;
    //结课时间
    private Date endTime;
    //状态：1：有效；2失效
    private Integer status;
    //get set 方法
    public void setPurchId (Integer  purchId){
        this.purchId=purchId;
    }
    public  Integer getPurchId(){
        return this.purchId;
    }
    public void setGoodsId (Integer  goodsId){
        this.goodsId=goodsId;
    }
    public  Integer getGoodsId(){
        return this.goodsId;
    }
    public void setUserId (Integer  userId){
        this.userId=userId;
    }
    public  Integer getUserId(){
        return this.userId;
    }
    public void setPrice (Integer  price){
        this.price=price;
    }
    public  Integer getPrice(){
        return this.price;
    }
    public void setValid (Integer  valid){
        this.valid=valid;
    }
    public  Integer getValid(){
        return this.valid;
    }
    public void setStartTime (Date  startTime){
        this.startTime=startTime;
    }
    public  Date getStartTime(){
        return this.startTime;
    }
    public void setEndTime (Date  endTime){
        this.endTime=endTime;
    }
    public  Date getEndTime(){
        return this.endTime;
    }
    public void setStatus (Integer  status){
        this.status=status;
    }
    public  Integer getStatus(){
        return this.status;
    }
}
