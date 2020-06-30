package com.zb.mapper;

import com.zb.pojo.Taskhistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface TaskhistoryMapper {

	public Taskhistory getTaskhistoryById(@Param(value = "id") Long id)throws Exception;

	public List<Taskhistory>	getTaskhistoryListByMap(Map<String, Object> param)throws Exception;

	public Integer getTaskhistoryCountByMap(Map<String, Object> param)throws Exception;

	public Integer insertTaskhistory(Taskhistory taskhistory)throws Exception;

	public Integer updateTaskhistory(Taskhistory taskhistory)throws Exception;

	public Integer deleteTaskhistoryById(@Param(value = "id") Long id)throws Exception;

	public Integer batchDeleteTaskhistory(Map<String, List<String>> params);

}
