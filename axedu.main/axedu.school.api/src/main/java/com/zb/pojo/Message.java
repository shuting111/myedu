package com.zb.pojo;

public class Message {
    private Integer messageid;
    private String messagetitle;
    private Integer messagetype;
    private Integer ishot;
    private String messageContent;
    private String publishDate;
    private String resourch;
    private String author;
    private Integer lookcount;
    private String imgurl;

    public Message() {
        super();
    }

    public Message(Integer messageid, String messagetitle, Integer messagetype, Integer ishot, String messageContent, String publishDate, String resourch, String author, Integer lookcount, String imgurl) {
        this.messageid = messageid;
        this.messagetitle = messagetitle;
        this.messagetype = messagetype;
        this.ishot = ishot;
        this.messageContent = messageContent;
        this.publishDate = publishDate;
        this.resourch = resourch;
        this.author = author;
        this.lookcount = lookcount;
        this.imgurl = imgurl;
    }

    public Integer getMessageid() {
        return messageid;
    }

    public void setMessageid(Integer messageid) {
        this.messageid = messageid;
    }

    public String getMessagetitle() {
        return messagetitle;
    }

    public void setMessagetitle(String messagetitle) {
        this.messagetitle = messagetitle;
    }

    public Integer getMessagetype() {
        return messagetype;
    }

    public void setMessagetype(Integer messagetype) {
        this.messagetype = messagetype;
    }

    public Integer getIshot() {
        return ishot;
    }

    public void setIshot(Integer ishot) {
        this.ishot = ishot;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getResourch() {
        return resourch;
    }

    public void setResourch(String resourch) {
        this.resourch = resourch;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getLookcount() {
        return lookcount;
    }

    public void setLookcount(Integer lookcount) {
        this.lookcount = lookcount;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }
}
