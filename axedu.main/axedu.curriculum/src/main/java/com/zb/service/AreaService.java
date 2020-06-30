package com.zb.service;

import com.zb.pojo.Area;

import java.util.List;

/**
 * @author 王淑婷
 * @Description TODO
 * @Date 2020/6/17
 * @Version V1.0
 */
public interface AreaService {
    //查询省级地区
    public List<Area> findAreaByLevel();
}
