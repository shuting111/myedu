package com.zb.mapper;

import com.zb.pojo.Subject;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface SubjectMapper {

	public Subject getSubjectById(@Param(value = "id") Long id)throws Exception;

	public List<Subject> getSubjectListByMap(Map<String, Object> param)throws Exception;

	public Integer getSubjectCountByMap(Map<String, Object> param)throws Exception;

	public Integer insertSubject(Subject subject)throws Exception;

	public Integer updateSubject(Subject subject)throws Exception;

	//根据年级id查询对应的科目
	public List<Subject> findSubjectByGradeId(@Param("gid") Integer gid);



}
