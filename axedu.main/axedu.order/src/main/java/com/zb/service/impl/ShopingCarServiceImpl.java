package com.zb.service.impl;

import com.zb.pojo.Cart;
import com.zb.pojo.Curriculum;
import com.zb.pojo.Shoping;
import com.zb.service.CurriculumService;
import com.zb.service.ShopingCarService;
import com.zb.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ShopingCarServiceImpl implements ShopingCarService {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private CurriculumService curriculumService;

    @Override
    public boolean addCart(Integer uid, Integer subjectId, Integer num) {
        Map<String ,Object> data = new HashMap<>();
        data.put(subjectId+"",num);
        boolean hmset = redisUtil.hmset("subject:" + uid, data);
        return  hmset;
    }

    @Override
    public void updateNum(Integer uid, Integer subjectId, Integer num, String op) {
        //获取购物车中该商品个数
        int i = this.getValue(uid, subjectId);
        if(op.equals("add")){
            //根据id获得商品信息
            Curriculum curriculum = curriculumService.findCurriculumById(subjectId);
            //如果是非特价课程
            if(curriculum.getOriginalPrice().equals(curriculum.getPresentPrice())){
                //获取剩余名额
                Integer banrong = curriculum.getBanrong();
                //可以添加数量
                if(banrong>(i+num)){
                    redisUtil.hincr("subject:"+uid,subjectId+"",num);
                }
            }else{
                //特价课程只能购买一个
                return;
            }
        }else{
            //减少课程时最少为0
            if(i-num>0){
                redisUtil.hdecr("subject:"+uid,subjectId+"",num);
            }
        }
    }

    @Override
    public void delItem(Integer uid, Integer subjectId) {
        redisUtil.hdel("subject:"+uid,subjectId+"");
    }

    @Override
    public Cart getCartAllItem(Integer uid) {
        Cart cart = new Cart();
        List<Shoping>list  =new ArrayList<>();
        Map<Object, Object> all = redisUtil.getAll("subject:" + uid);
        Iterator<Object> it =all.keySet().iterator();
        while (it.hasNext()){
            Object key = it.next();//商品编号
            Object val = all.get(key);//商品数量
            System.out.println(key+"\t"+val);
            Shoping shoping = new Shoping();
            shoping.setShopingId(Integer.parseInt(key.toString()));
            shoping.setNumber(Integer.parseInt(val.toString()));
            String shopingKey = "shoping:"+shoping.getShopingId();
            Map<Object, Object> hmget = redisUtil.hmget(shopingKey);
            shoping.setShopingName(hmget.get("subjectName").toString());
            list.add(shoping);
        }
        cart.setItems(list);
        return cart;
    }

    @Override
    public int getValue(Integer uid, Integer subjectId) {
        String key = "subject:" + uid;
        Map<Object, Object> hmget = redisUtil.hmget(key);
        String string = hmget.get(subjectId + "").toString();
        return Integer.parseInt(string);
    }
}
