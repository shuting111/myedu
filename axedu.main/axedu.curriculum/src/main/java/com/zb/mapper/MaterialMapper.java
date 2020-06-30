package com.zb.mapper;

import com.zb.pojo.Material;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface MaterialMapper {

	public Material getMaterialById(@Param(value = "id") Long id)throws Exception;

	public List<Material>	getMaterialListByMap(Map<String, Object> param)throws Exception;

	public Integer getMaterialCountByMap(Map<String, Object> param)throws Exception;

	public Integer insertMaterial(Material material)throws Exception;

	public Integer updateMaterial(Material material)throws Exception;


}
