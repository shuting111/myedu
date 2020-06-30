package com.zb.mapper;

import com.zb.pojo.Curriculum;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface CurriculumMapper {

	public Curriculum getCurriculumById(@Param(value = "id") Long id)throws Exception;

	public List<Curriculum>	getCurriculumListByMap(Map<String, Object> param)throws Exception;

	public Integer getCurriculumCountByMap(Map<String, Object> param)throws Exception;

	public Integer insertCurriculum(Curriculum curriculum)throws Exception;

	public Integer updateCurriculum(Curriculum curriculum)throws Exception;

	/**
	 * 查找特价课程
	 * @return
	 */
	public List<Curriculum>  findIsDiscount();






}
