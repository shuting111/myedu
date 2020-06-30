package com.zb.service.impl;

import com.zb.dto.Page;
import com.zb.mapper.MessageMapper;
import com.zb.pojo.Message;
import com.zb.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageMapper mapper;
    @Override
    public Page<Message> messagePage(Integer curPage, Integer pageSize) {
        Page<Message> page=new Page<>();
        int count=mapper.findCount();
        List<Message> list=mapper.findList((curPage-1)*pageSize,pageSize);
        page.setPageCount(count);
        page.setRows(list);
        page.setCurPage(curPage);
        page.setPageSize(pageSize);
         return page;
    }

    @Override
    public List<Message> findById(Integer id) {
        return mapper.findById(id);
    }
}
