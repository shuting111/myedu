package com.zb.service;


import com.zb.pojo.Curriculum;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CurriculumService {


    /**
     * 根据id查询课程
     * @param id
     * @return
     */
    public Curriculum findCurriculumById(Integer id);

    /**
     * 查询所有优惠课程
     */
    public List<Curriculum>findIsDiscount();

    /**
     * 购买普通课程修改名额
     * @param id
     * @param banrong
     * @return
     */
    public int updateBanrong(Integer id, Integer banrong);
}
