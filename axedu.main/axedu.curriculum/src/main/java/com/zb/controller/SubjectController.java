package com.zb.controller;

import com.zb.pojo.Subject;
import com.zb.service.SubjectService;
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
public class SubjectController {
    @Autowired
    private SubjectService subjectService;
//查询全部课程
    @GetMapping("/findSubject")
    public List<Subject> findSubject(){
        return subjectService.findSubject();
    }


}
