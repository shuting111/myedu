package com.zb.service;

import com.zb.pojo.Advert;

import java.util.List;

/**
 * @author 王淑婷
 * @Description TODO
 * @Date 2020/6/16
 * @Version V1.0
 */
public interface AdvertService {
    //轮播图的图片
    public List<Advert> getAdvertUrl(Integer id);
}
