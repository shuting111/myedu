package com.zb.service;

import com.zb.dto.Page;
import com.zb.pojo.Message;
import io.swagger.models.auth.In;

import java.util.List;

public interface MessageService {
    public Page<Message> messagePage(Integer curPage,Integer pageSize);

    public List<Message> findById(Integer id);
}
