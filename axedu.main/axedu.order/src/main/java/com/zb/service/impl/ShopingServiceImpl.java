package com.zb.service.impl;

import com.zb.service.ShopingService;
import com.zb.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ShopingServiceImpl implements ShopingService {

    @Autowired
    private RedisUtil redisUtil;
    @Override
    public void addShoping(Integer subjectId, Integer price, String subjectName) {
        Map<String,Object>data = new HashMap<>();
        data.put("subjectId",subjectId);
        data.put("subjectName",subjectName);
        data.put("price",price);
        redisUtil.hmset("shoping:"+subjectId,data);
    }
}
