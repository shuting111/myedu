package com.zb.service.impl;

import com.zb.pojo.Advert;
import com.zb.service.AdvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author 王淑婷
 * @Description TODO
 * @Date 2020/6/16
 * @Version V1.0
 */
@Service
public class AdvertServiceImpl implements AdvertService {
    @Autowired
    private RestTemplate restTemplate;
    @Override
    public List<Advert> getAdvertUrl(Integer id) {
        String url = "http://localhost:9000/axedu?id=" + id;
        List<Advert> list = restTemplate.getForObject(url, List.class);
        return list;
    }
}
