package com.zb.mapper;

import com.zb.pojo.Grade;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface GradeMapper {

	public Grade getGradeById(@Param(value = "id") Long id)throws Exception;

	public List<Grade>	getGradeListByMap(Map<String, Object> param)throws Exception;

	public Integer getGradeCountByMap(Map<String, Object> param)throws Exception;

	public Integer insertGrade(Grade grade)throws Exception;

	public Integer updateGrade(Grade grade)throws Exception;

	//查询全部年级
	public List<Grade> findGrade();





}
