package com.zb.mapper;

import com.zb.pojo.Message;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
@Mapper
public interface MessageMapper {
    public List<Message> findList(@Param("start") Integer start,@Param("pageSize") Integer size);
    public  int findCount();
    public List<Message> findById( @Param("id") Integer id);
}
