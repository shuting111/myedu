package com.zb.service.impl;

import com.netflix.discovery.converters.Auto;
import com.zb.mapper.TaskMapper;
import com.zb.pojo.Task;
import com.zb.service.TaskService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired(required = false)
    private TaskMapper taskMapper;
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Override
    public Integer insertTask(Task task) {
        try {
            return taskMapper.insertTask(task);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<Task> findOneMinute() {
        return taskMapper.findOneMinute();
    }

    @Override
    public Integer updateVersion(String taskId,Integer version) {
        Task task = new Task();
        task.setTaskId(taskId);
        task.setVersion(version);
        return taskMapper.updateVersion(task);
    }

    @Override
    public void publishTask(Task task) {
        Task tasks = this.getTaskById(Long.parseLong(task.getTaskId() + ""));
        if(tasks!=null){
            amqpTemplate.convertAndSend(tasks.getMyExchange(),tasks.getMyRoutingKey(),tasks);
        }
    }

    @Override
    public Task getTaskById(Long id) {
        try {
            return taskMapper.getTaskById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer updateTask(Task task) {
        try {
            return taskMapper.updateTask(task);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


}
