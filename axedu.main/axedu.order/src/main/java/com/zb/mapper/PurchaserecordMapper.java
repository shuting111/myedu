package com.zb.mapper;

import com.zb.pojo.Purchaserecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface PurchaserecordMapper {

	public Purchaserecord getPurchaserecordById(@Param(value = "id") Long id)throws Exception;

	public List<Purchaserecord>	getPurchaserecordListByMap(Map<String, Object> param)throws Exception;

	public Integer getPurchaserecordCountByMap(Map<String, Object> param)throws Exception;

	public Integer insertPurchaserecord(Purchaserecord purchaserecord)throws Exception;

	public Integer updatePurchaserecord(Purchaserecord purchaserecord)throws Exception;

	public Integer deletePurchaserecordById(@Param(value = "id") Long id)throws Exception;

	public Integer batchDeletePurchaserecord(Map<String, List<String>> params);

}
