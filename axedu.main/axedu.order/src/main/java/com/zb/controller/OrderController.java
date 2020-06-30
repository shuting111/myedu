package com.zb.controller;

import com.zb.pojo.Orders;
import com.zb.service.OrderService;
import com.zb.util.OrderCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/insertOrders")
    public Integer insertOrders(Orders orders){
        return orderService.insertOrders(orders);
    }

    @GetMapping("/qgCurriculum/{subjectId}/{token}")
    public String qgCurriculum(@PathVariable("subjectId") Integer subjectId, @PathVariable("token") String token){
        return orderService.qgCurriculum(subjectId,token);
    }

    @PostMapping("/insertQgOrders")
    public Integer insertQgOrders(Orders orders){
        orders.setOrderNo(OrderCode.getOrderCode());
        return orderService.insertQgOrders(orders);
    }

}
