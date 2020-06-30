package com.zb.controller;

import com.zb.service.OrderTempService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class OrderTempController {
    @Autowired
    private OrderTempService orderTempService;
    @GetMapping("/getTempCount/{id}")
    public int getTempCount(@PathVariable("id") Integer id){
        return orderTempService.getTempCount(id);
    }

    @PostMapping("/lockRoomStock")
    public int lockRoomStock(Integer id,Integer uid){
        return orderTempService.lockRoomStock(id, uid);
    }

    @GetMapping("/qgWhile/{subjectId}")
    public String qgWhile(@PathVariable("subjectId") Integer subjectId){
        return orderTempService.qgWhile(subjectId);
    }

}
