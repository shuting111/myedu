package com.zb.pojo;
import java.io.Serializable;
import java.util.Date;
/***
*   
*/
public class Shoping implements Serializable {
    //购物车编号
    private Integer sid;
    //商品id
    private Integer shopingId;
    //商品名称
    private String shopingName;
    //0:未付款；1：已付款；2：已取消
    private Integer shopingStatus;
    //用户Id
    private Integer userId;
    //价格
    private Double shopingPrice;
    //创建时间
    private Date createTime;
    //商品数量
    private Integer number;
    //get set 方法
    public void setSid (Integer  sid){
        this.sid=sid;
    }
    public  Integer getSid(){
        return this.sid;
    }
    public void setShopingId (Integer  shopingId){
        this.shopingId=shopingId;
    }
    public  Integer getShopingId(){
        return this.shopingId;
    }
    public void setShopingName (String  shopingName){
        this.shopingName=shopingName;
    }
    public  String getShopingName(){
        return this.shopingName;
    }
    public void setShopingStatus (Integer  shopingStatus){
        this.shopingStatus=shopingStatus;
    }
    public  Integer getShopingStatus(){
        return this.shopingStatus;
    }
    public void setUserId (Integer  userId){
        this.userId=userId;
    }
    public  Integer getUserId(){
        return this.userId;
    }
    public void setShopingPrice (Double  shopingPrice){
        this.shopingPrice=shopingPrice;
    }
    public  Double getShopingPrice(){
        return this.shopingPrice;
    }
    public void setCreateTime (Date  createTime){
        this.createTime=createTime;
    }
    public  Date getCreateTime(){
        return this.createTime;
    }
    public void setNumber (Integer  number){
        this.number=number;
    }
    public  Integer getNumber(){
        return this.number;
    }
}
