package com.zb.controller;

import com.zb.service.ShopingCarService;
import com.zb.service.ShopingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShopingController {
    @Autowired
    private ShopingService shopingService;
    @Autowired
    private ShopingCarService shopingCarService;
    @PostMapping("/addShoping")
    public void addShoping(Integer subjectId, Integer price, String subjectName, Integer uid,Integer num){
        shopingService.addShoping(subjectId, price, subjectName);
        shopingCarService.addCart(uid,subjectId,num);
    }
}
