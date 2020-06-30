package com.zb.controller;

import com.zb.pojo.Curriculum;
import com.zb.service.CurriculumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class CurriculumController {
    @Autowired
    private CurriculumService curriculumService;

    @GetMapping("/findIsDiscount")
    public List<Curriculum> findIsDiscount(){
        return curriculumService.findIsDiscount();
    }

    @GetMapping("/findCurriculumById/{id}")
    public Curriculum findCurriculumById(@PathVariable("id") Integer id){
        return curriculumService.findCurriculumById(id);
    }

    @GetMapping("/updateBanrong/{id}/{banrong}")
    public int updateBanrong(@PathVariable("id") Integer id, @PathVariable("banrong") Integer banrong) {
        return curriculumService.updateBanrong(id,banrong);
    }
}
