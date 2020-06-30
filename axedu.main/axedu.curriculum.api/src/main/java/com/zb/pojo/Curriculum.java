package com.zb.pojo;






import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/***
*   
*/
public class Curriculum implements Serializable {
    //课程id
    private Integer id;
    //课程名称
    @NotNull(message = "课程名字不能为空")
    private String className;
    //科目id
    private Integer subjectId;
    //科目
    private String subName;
    //年级id
    private Integer gradeId;
    //年级名称
    private String gradeName;
    //上课时间（1.周末2.非周末）
    private Integer classtime;
    //授课方式（1.一对一2.一对二3.一对三4.小班课）
    private Integer teachingMethod;
    //学校id
    private Integer schoolId;
    //原价
    private Double originalPrice;
    //现价
    private Double presentPrice;
    //班容
    private Integer banrong;
    //开班规律
    private String openingRule;
    //课程介绍
    private String classIntroduce;
    //教学目标
    private String teachingTarget;
    //授课特点
    private String teachingFeatures;
    //授课内容
    private String teachingContent;
    //是否是精选（1.是2.否）
    private Integer isChoiceness;
    //详情的图片
    private String detailsimg;
    //页面的图片
    private String pageimg;
    //上课地点
    private Integer areaid;
    //发布时间
    private String createtime;
    //点击量
    private Integer hits=0;
    //猜你喜欢
    private Integer like;
    //课次
    private Integer classnum;
    //课程的评价
    private Double score;

    //get set 方法


    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public Integer getClassnum() {
        return classnum;
    }

    public void setClassnum(Integer classnum) {
        this.classnum = classnum;
    }

    public Integer getAreaid() {
        return areaid;
    }

    public void setAreaid(Integer areaid) {
        this.areaid = areaid;
    }

    public Integer getLike() {
        return like;
    }

    public void setLike(Integer like) {
        this.like = like;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public Integer getHits() {
        return hits;
    }

    public void setHits(Integer hits) {
        this.hits = hits;
    }


    public void setId (Integer  id){
        this.id=id;
    }
    public  Integer getId(){
        return this.id;
    }
    public void setClassName (String  className){
        this.className=className;
    }
    public  String getClassName(){
        return this.className;
    }
    public void setSubjectId (Integer  subjectId){
        this.subjectId=subjectId;
    }
    public  Integer getSubjectId(){
        return this.subjectId;
    }
    public void setGradeId (Integer  gradeId){
        this.gradeId=gradeId;
    }
    public  Integer getGradeId(){
        return this.gradeId;
    }
    public void setClasstime (Integer  classtime){
        this.classtime=classtime;
    }
    public  Integer getClasstime(){
        return this.classtime;
    }
    public void setTeachingMethod (Integer  teachingMethod){
        this.teachingMethod=teachingMethod;
    }
    public  Integer getTeachingMethod(){
        return this.teachingMethod;
    }
    public void setSchoolId (Integer  schoolId){
        this.schoolId=schoolId;
    }
    public  Integer getSchoolId(){
        return this.schoolId;
    }
    public void setOriginalPrice (Double  originalPrice){
        this.originalPrice=originalPrice;
    }
    public  Double getOriginalPrice(){
        return this.originalPrice;
    }
    public void setPresentPrice (Double  presentPrice){
        this.presentPrice=presentPrice;
    }
    public  Double getPresentPrice(){
        return this.presentPrice;
    }
    public void setBanrong (Integer  banrong){
        this.banrong=banrong;
    }
    public  Integer getBanrong(){
        return this.banrong;
    }
    public void setOpeningRule (String  openingRule){
        this.openingRule=openingRule;
    }
    public  String getOpeningRule(){
        return this.openingRule;
    }
    public void setClassIntroduce (String  classIntroduce){
        this.classIntroduce=classIntroduce;
    }
    public  String getClassIntroduce(){
        return this.classIntroduce;
    }
    public void setTeachingTarget (String  teachingTarget){
        this.teachingTarget=teachingTarget;
    }
    public  String getTeachingTarget(){
        return this.teachingTarget;
    }
    public void setTeachingFeatures (String  teachingFeatures){
        this.teachingFeatures=teachingFeatures;
    }
    public  String getTeachingFeatures(){
        return this.teachingFeatures;
    }
    public void setTeachingContent (String  teachingContent){
        this.teachingContent=teachingContent;
    }
    public  String getTeachingContent(){
        return this.teachingContent;
    }
    public void setIsChoiceness (Integer  isChoiceness){
        this.isChoiceness=isChoiceness;
    }
    public  Integer getIsChoiceness(){
        return this.isChoiceness;
    }
    public void setDetailsimg (String  detailsimg){
        this.detailsimg=detailsimg;
    }
    public  String getDetailsimg(){
        return this.detailsimg;
    }
    public void setPageimg (String  pageimg){
        this.pageimg=pageimg;
    }
    public  String getPageimg(){
        return this.pageimg;
    }
}
