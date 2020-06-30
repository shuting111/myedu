package com.zb.job;

import com.zb.pojo.Task;
import com.zb.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MyTaskJob {
    @Autowired
    private TaskService taskService;


    @Scheduled(cron = "* 0/1 * * * *")
    public void send(){
        List<Task> oneMinute = taskService.findOneMinute();
        for(Task t:oneMinute){
            System.out.println(t.getMyExchange()+"\t"+t.getMyRoutingKey());
            int num = taskService.updateVersion(t.getTaskId(),t.getVersion());
            if(num>0){
                taskService.publishTask(t);
                t.setVersion(-1);
                taskService.updateTask(t);
            }
        }
    }
}
