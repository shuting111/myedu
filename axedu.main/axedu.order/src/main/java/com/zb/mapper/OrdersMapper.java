package com.zb.mapper;

import com.zb.pojo.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrdersMapper {

	public Orders getOrdersById(@Param(value = "id") Long id)throws Exception;

	public List<Orders>	getOrdersListByMap(Map<String, Object> param)throws Exception;

	public Integer getOrdersCountByMap(Map<String, Object> param)throws Exception;

	public Integer insertOrders(Orders orders)throws Exception;

	public Integer updateOrders(Orders orders)throws Exception;

	public Orders findByOrderNo(@Param("orderNo")String orderNo);

}
