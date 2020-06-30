package com.zb.controller;

import com.zb.pojo.Area;
import com.zb.service.AreaService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 王淑婷
 * @Description TODO
 * @Date 2020/6/17
 * @Version V1.0
 */
@RestController
@CrossOrigin
public class AreaController {
    @Autowired
    private AreaService areaService;

    @GetMapping("/findAreaByLevel")
    public List<Area> findAreaByLevel(){
        return areaService.findAreaByLevel();
    }
}
