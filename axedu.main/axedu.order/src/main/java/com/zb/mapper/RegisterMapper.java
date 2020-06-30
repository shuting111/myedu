package com.zb.mapper;
import com.zb.pojo.Register;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RegisterMapper {

	public Register getRegisterById(@Param(value = "id") Long id)throws Exception;

	public List<Register>	getRegisterListByMap(Map<String,Object> param)throws Exception;

	public Integer getRegisterCountByMap(Map<String,Object> param)throws Exception;

	public Integer insertRegister(Register register)throws Exception;

	public Integer updateRegister(Register register)throws Exception;

}
