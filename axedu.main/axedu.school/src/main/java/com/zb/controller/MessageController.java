package com.zb.controller;

import com.zb.dto.Page;
import com.zb.pojo.Message;
import com.zb.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class MessageController {
    @Autowired
    private MessageService messageService;
    @PostMapping(value = "/messagePage")
    public Page<Message> messagePage(Integer curPage,Integer pageSize){
        return  messageService.messagePage(curPage,pageSize);
    }

    @GetMapping(value = "/findById/{id}")
    public List<Message> findById(@PathVariable("id") Integer id){
        return messageService.findById(id);
    }
}
