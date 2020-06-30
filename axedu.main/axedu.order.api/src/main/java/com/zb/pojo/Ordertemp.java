package com.zb.pojo;
import java.io.Serializable;
import java.util.Date;
/***
*   
*/
public class Ordertemp implements Serializable {
    //
    private Integer tempId;
    //商品id
    private Integer goodsId;
    //记录时间
    private String recordTime;
    //剩余名额
    private Integer store;
    //状态：0：未购买；1:已购买；2：购买失败
    private Integer status;
    //用户id
    private Integer userId;
    //get set 方法
    public void setTempId (Integer  tempId){
        this.tempId=tempId;
    }
    public  Integer getTempId(){
        return this.tempId;
    }
    public void setGoodsId (Integer  goodsId){
        this.goodsId=goodsId;
    }
    public  Integer getGoodsId(){
        return this.goodsId;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public void setStore (Integer  store){
        this.store=store;
    }
    public  Integer getStore(){
        return this.store;
    }
    public void setStatus (Integer  status){
        this.status=status;
    }
    public  Integer getStatus(){
        return this.status;
    }
    public void setUserId (Integer  userId){
        this.userId=userId;
    }
    public  Integer getUserId(){
        return this.userId;
    }
}
