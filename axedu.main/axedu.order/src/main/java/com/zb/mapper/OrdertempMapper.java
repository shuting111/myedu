package com.zb.mapper;

import com.zb.pojo.Ordertemp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrdertempMapper {

	public Ordertemp getOrdertempById(@Param(value = "id") Long id)throws Exception;

	public List<Ordertemp>	getOrdertempListByMap(Map<String, Object> param)throws Exception;

	public Integer getOrdertempCountByMap(Map<String, Object> param)throws Exception;

	public Integer insertOrdertemp(Ordertemp ordertemp)throws Exception;

	public Integer updateOrdertemp(Ordertemp ordertemp)throws Exception;

	/**
	 * 查询临时表中某个商品信息
	 * @param id
	 * @param uid
	 * @return
	 */
	public Ordertemp findOnlyOne(@Param("id") Integer id,@Param("uid") Integer uid);

	/**
     * 临时库存表中该商品记录数
	 * @param id
     * @return
     */
	public int getTempCount(@Param("id") Integer id);

}
