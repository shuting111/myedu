package com.zb.service;

import com.zb.pojo.Orders;
import org.apache.ibatis.annotations.Param;

public interface OrderService {

    public Integer insertOrders(Orders orders);

    public String qgCurriculum(Integer subjectId, String token);

    public Integer insertQgOrders(Orders orders);

    public Orders findByOrderNo(String orderNo);

    public Integer updateOrders(Orders orders);

    public Integer updateSuccessOrders(Orders orders);
}
