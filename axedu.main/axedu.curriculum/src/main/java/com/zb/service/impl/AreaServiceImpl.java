package com.zb.service.impl;

import com.alibaba.fastjson.JSON;
import com.zb.mapper.AreaMapper;
import com.zb.pojo.Area;
import com.zb.service.AreaService;
import com.zb.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 王淑婷
 * @Description TODO
 * @Date 2020/6/17
 * @Version V1.0
 */
@Service
public class AreaServiceImpl implements AreaService {
    @Autowired(required = false)
    private AreaMapper areaMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Override
    public List<Area> findAreaByLevel() {
        String key = "area";
        List<Area> list = null;
        if(redisUtil.hasKey(key)){
            System.out.println("从Redis里查找");
            list = JSON.parseArray(redisUtil.get(key).toString(),Area.class);
        }else{
            System.out.println("从数据库里查找");
            Map<String , Object> map = new HashMap<>();
            map.put("level",1);
            try {
                list = areaMapper.getAreaListByMap(map);
                for (Area area : list) {
                    Map<String,Object> param = new HashMap<>();
                    param.put("parent",area.getId());
                    List<Area> child = areaMapper.getAreaListByMap(param);
                    for (Area a : child) {
                        Map<String,Object> param1 = new HashMap<>();
                        param1.put("parent",a.getId());
                        List<Area> child1 = areaMapper.getAreaListByMap(param1);
                        a.setChild(child1);
                    }
                    area.setChild(child);
                }
                redisUtil.set(key,JSON.toJSONString(list));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return list;
    }
}
