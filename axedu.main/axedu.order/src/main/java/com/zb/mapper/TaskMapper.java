package com.zb.mapper;

import com.zb.pojo.Task;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface TaskMapper {

	public Task getTaskById(@Param(value = "id") Long id)throws Exception;

	public List<Task>	getTaskListByMap(Map<String, Object> param)throws Exception;

	public Integer getTaskCountByMap(Map<String, Object> param)throws Exception;

	public Integer insertTask(Task task)throws Exception;

	public Integer updateTask(Task task)throws Exception;

	public List<Task> findOneMinute();

	public Integer updateVersion(Task task);

}
