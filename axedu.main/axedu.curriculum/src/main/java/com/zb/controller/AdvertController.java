package com.zb.controller;

import com.zb.pojo.Advert;
import com.zb.service.AdvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 王淑婷
 * @Description TODO
 * @Date 2020/6/16
 * @Version V1.0
 */
@RestController
@CrossOrigin
public class AdvertController {
    @Autowired
    private AdvertService advertService;

    //根据广告的类型查询对应的广告图片集合
    @GetMapping("/getAdvertUrl/{id}")
    public List<Advert> getAdvertUrl(@PathVariable("id") Integer id){
        return advertService.getAdvertUrl(id);
    }
}
