package com.zb.service;

import com.zb.pojo.Task;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TaskService {
    public Integer insertTask(Task task);

    /**
     * 查询一分钟之前的数据
     * @return
     */
    public List<Task> findOneMinute();

    /**
     * 乐观锁
     * @return
     */
    public Integer updateVersion(String taskId,Integer version);

    /**
     * 发送数据
     * @param task
     */
    public void publishTask(Task task);


    /**
     * 查询要发送的数据
     * @param id
     * @return
     */
    public Task getTaskById(Long id);

    /**
     * 修改当前时间
     * @param task
     * @return
     */
    public Integer updateTask(Task task);
}
