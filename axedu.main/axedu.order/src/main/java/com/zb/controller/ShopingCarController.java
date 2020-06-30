package com.zb.controller;

import com.zb.pojo.Cart;
import com.zb.pojo.Shoping;
import com.zb.service.ShopingCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ShopingCarController {
    @Autowired
    private ShopingCarService shopingCarService;
    @PostMapping("/addCart")
    public boolean addCart(Integer uid, Integer subjectId, Integer num){
        return shopingCarService.addCart(uid, subjectId, num);
    }

    @PostMapping("/updateNum")
    public void updateNum(Integer uid, Integer subjectId, Integer num, String op){
        shopingCarService.updateNum(uid, subjectId, num, op);
    }

    @PostMapping("/delItem")
    public void delItem(Integer uid, Integer subjectId){
        shopingCarService.delItem(uid,subjectId);
    }

    @PostMapping("/getCartAllItem")
    public Cart getCartAllItem(Integer uid){
        Cart cart = shopingCarService.getCartAllItem(uid);
        List<Shoping> shoping = cart.getItems();
        for (Shoping s : shoping) {
            System.out.println(s.getShopingName()+"\t"+s.getNumber()+"\t"+s.getShopingId());
        }
        return cart;
    }

    @PostMapping("/getValue")
    public int getValue(Integer uid, Integer subjectId){
        return shopingCarService.getValue(uid, subjectId);
    }
}
