package com.zb.pojo;
import java.io.Serializable;
import java.util.List;

/***
*   区域字典表
*/
public class Area implements Serializable {
    //主键
    private Long id;
    //区域名称
    private String name;
    //区域编号
    private String areaNo;
    //父级区域
    private Long parent;
    //区域级别(0:国家级 1:省级 2:市级 3:县/区)
    private Integer level;
    //1:国内 2：国外
    private Integer isChina;
    //该级别下的子区域
    private List<Area> child;



    //get set 方法


    public List<Area> getChild() {
        return child;
    }

    public void setChild(List<Area> child) {
        this.child = child;
    }

    public void setId (Long  id){
        this.id=id;
    }
    public  Long getId(){
        return this.id;
    }
    public void setName (String  name){
        this.name=name;
    }
    public  String getName(){
        return this.name;
    }
    public void setAreaNo (String  areaNo){
        this.areaNo=areaNo;
    }
    public  String getAreaNo(){
        return this.areaNo;
    }
    public void setParent (Long  parent){
        this.parent=parent;
    }
    public  Long getParent(){
        return this.parent;
    }
    public void setLevel (Integer  level){
        this.level=level;
    }
    public  Integer getLevel(){
        return this.level;
    }
    public void setIsChina (Integer  isChina){
        this.isChina=isChina;
    }
    public  Integer getIsChina(){
        return this.isChina;
    }

}
