package com.zb.feign;

import com.zb.pojo.Curriculum;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author 王淑婷
 * @Description TODO
 * @Date 2020/6/10
 * @Version V1.0
 */
@FeignClient(name = "curriculum-server")
public interface CurriculumFeignClient {
    //唯一查询接口
    @GetMapping("/findCurriById/{id}")
    public Curriculum findCurriById(@PathVariable("id") Integer id);
    //添加课程接口
    @PostMapping("/insertCurriculum")
    public int insertCurriculum(@RequestBody Curriculum curriculum);
}
