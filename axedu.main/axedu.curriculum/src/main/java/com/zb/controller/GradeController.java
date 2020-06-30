package com.zb.controller;

import com.zb.pojo.Grade;
import com.zb.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 王淑婷
 * @Description TODO
 * @Date 2020/6/16
 * @Version V1.0
 */
@RestController
@CrossOrigin
public class GradeController {
    @Autowired
    private GradeService gradeService;

    //根据年级等级查询对应的年级
    @GetMapping("/findListByParentId/{pid}")
    public List<Grade> findListByParentId(@PathVariable("pid") Integer pid){
        return gradeService.findListByParentId(pid);
    }
    //年级的全部查询
    @GetMapping("/findGrade")
    public List<Grade> findGrade(){
        return gradeService.findGrade();
    }
}
