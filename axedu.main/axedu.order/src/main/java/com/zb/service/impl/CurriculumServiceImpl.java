package com.zb.service.impl;

import com.zb.feign.CurriculumFeignClient;
import com.zb.mapper.CurriculumMapper;
import com.zb.pojo.Curriculum;
import com.zb.service.CurriculumService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurriculumServiceImpl implements CurriculumService {

    @Autowired
    private CurriculumFeignClient curriculumFeignClient;

    @Autowired(required = false)
    private CurriculumMapper curriculumMapper;

    @Override
    public Curriculum findCurriculumById(Integer id) {
        return curriculumFeignClient.findCurriById(id);
    }

    @Override
    public List<Curriculum> findIsDiscount() {
        return curriculumMapper.findIsDiscount();
    }

    @Override
    public int updateBanrong(Integer id, Integer banrong) {
        Curriculum curriculum = curriculumFeignClient.findCurriById(id);
        if((curriculum.getBanrong()-banrong)>0){
            return curriculumMapper.updateBanrong(id,banrong);
        }else{
            return 0;
        }
    }

}
