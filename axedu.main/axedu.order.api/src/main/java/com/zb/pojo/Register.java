package com.zb.pojo;
import java.io.Serializable;
import java.util.Date;
/***
*   
*/
public class Register implements Serializable {
    //
    private Integer id;
    //
    private String account;
    //
    private String password;
    //
    private Integer Administratorid;
    //
    private String phone;
    //
    private Integer age;
    //
    private String identity;
    //
    private String mail;
    //get set 方法
    public void setId (Integer  id){
        this.id=id;
    }
    public  Integer getId(){
        return this.id;
    }
    public void setAccount (String  account){
        this.account=account;
    }
    public  String getAccount(){
        return this.account;
    }
    public void setPassword (String  password){
        this.password=password;
    }
    public  String getPassword(){
        return this.password;
    }
    public void setAdministratorid (Integer  Administratorid){
        this.Administratorid=Administratorid;
    }
    public  Integer getAdministratorid(){
        return this.Administratorid;
    }
    public void setPhone (String  phone){
        this.phone=phone;
    }
    public  String getPhone(){
        return this.phone;
    }
    public void setAge (Integer  age){
        this.age=age;
    }
    public  Integer getAge(){
        return this.age;
    }
    public void setIdentity (String  identity){
        this.identity=identity;
    }
    public  String getIdentity(){
        return this.identity;
    }
    public void setMail (String  mail){
        this.mail=mail;
    }
    public  String getMail(){
        return this.mail;
    }
}
