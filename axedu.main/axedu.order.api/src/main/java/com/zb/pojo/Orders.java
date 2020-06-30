package com.zb.pojo;
import java.io.Serializable;
import java.util.Date;
/***
*   
*/
public class Orders implements Serializable {
    //订单编号
    private Integer oid;
    //商品编号
    private Integer goodsId;
    //用户编号
    private Integer userId;
    //订单号
    private String orderNo;
    //交易编号
    private String tradeNo;
    //订单名称
    private String orderName;
    //订单状态:0:未支付；1：已支付；2支付超时；3：已取消
    private Integer orderStatus;
    //支付金额
    private Double payAmount;
    //支付方式：1：微信；2：支付宝；3：现金
    private Integer paytype;
    //支付完成时间
    private String modifyTime;
    //联系手机号
    private String noticePhone;
    //联系邮箱
    private String noticeEmail;
    //开课时间
    private String startTime;
    //节课时间
    private String endTime;
    //报名人数
    private Integer number;
    //get set 方法
    public void setOid (Integer  oid){
        this.oid=oid;
    }
    public  Integer getOid(){
        return this.oid;
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
    public void setOrderNo (String  orderNo){
        this.orderNo=orderNo;
    }
    public  String getOrderNo(){
        return this.orderNo;
    }
    public void setTradeNo (String  tradeNo){
        this.tradeNo=tradeNo;
    }
    public  String getTradeNo(){
        return this.tradeNo;
    }
    public void setOrderName (String  orderName){
        this.orderName=orderName;
    }
    public  String getOrderName(){
        return this.orderName;
    }
    public void setOrderStatus (Integer  orderStatus){
        this.orderStatus=orderStatus;
    }
    public  Integer getOrderStatus(){
        return this.orderStatus;
    }
    public void setPayAmount (Double  payAmount){
        this.payAmount=payAmount;
    }
    public  Double getPayAmount(){
        return this.payAmount;
    }
    public void setPaytype (Integer  paytype){
        this.paytype=paytype;
    }
    public  Integer getPaytype(){
        return this.paytype;
    }

    public void setNoticePhone (String  noticePhone){
        this.noticePhone=noticePhone;
    }
    public  String getNoticePhone(){
        return this.noticePhone;
    }
    public void setNoticeEmail (String  noticeEmail){
        this.noticeEmail=noticeEmail;
    }
    public  String getNoticeEmail(){
        return this.noticeEmail;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setNumber (Integer  number){
        this.number=number;
    }
    public  Integer getNumber(){
        return this.number;
    }
}
