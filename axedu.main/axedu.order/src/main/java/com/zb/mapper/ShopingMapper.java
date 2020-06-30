package com.zb.mapper;

import com.zb.pojo.Shoping;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ShopingMapper {

	public Shoping getShopingById(@Param(value = "id") Long id)throws Exception;

	public List<Shoping>	getShopingListByMap(Map<String, Object> param)throws Exception;

	public Integer getShopingCountByMap(Map<String, Object> param)throws Exception;

	public Integer insertShoping(Shoping shoping)throws Exception;

	public Integer updateShoping(Shoping shoping)throws Exception;

	public Integer deleteShopingById(@Param(value = "id") Long id)throws Exception;

	public Integer batchDeleteShoping(Map<String, List<String>> params);

}
