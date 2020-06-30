package com.zb.mapper;

import com.zb.pojo.Area;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface AreaMapper {

	public Area getAreaById(@Param(value = "id") Long id)throws Exception;

	public List<Area>	getAreaListByMap(Map<String, Object> param)throws Exception;

	public Integer getAreaCountByMap(Map<String, Object> param)throws Exception;

	public Integer insertArea(Area area)throws Exception;

	public Integer updateArea(Area area)throws Exception;


}
