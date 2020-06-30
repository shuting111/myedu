package com.zb.service.impl;

import com.alibaba.fastjson.JSON;
import com.zb.mapper.OrdertempMapper;
import com.zb.pojo.Curriculum;
import com.zb.pojo.Ordertemp;
import com.zb.service.CurriculumService;
import com.zb.service.OrderTempService;
import com.zb.util.IdWorker;
import com.zb.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OrderTempServiceImpl implements OrderTempService {

    @Autowired(required = false)
    private OrdertempMapper ordertempMapper;
    @Autowired
    private CurriculumService curriculumService;
    @Autowired
    private RedisUtil redisUtil;
    @Override
    public int getTempCount(Integer id) {
        return ordertempMapper.getTempCount(id);
    }

    @Scheduled(cron = "0 0 2 * * ?")
    @Override
    public void CurriculumToRedis() {
        List<Curriculum> isDiscount = curriculumService.findIsDiscount();
        for (int i = 0; i < isDiscount.size(); i++){
            Curriculum curriculum = isDiscount.get(i);
            System.out.println(curriculum.getId());
            int tempCount = this.getTempCount(curriculum.getId());
            curriculum.setBanrong(curriculum.getBanrong()-tempCount);
            redisUtil.set("curriculum:"+curriculum.getId(),JSON.toJSONString(curriculum),60*60*24);
        }
    }

    @Override
    public Ordertemp findOnlyOne(Integer id, Integer uid) {
        return ordertempMapper.findOnlyOne(id,uid);
    }

    @Override
    public Integer updateOrdertemp(Ordertemp ordertemp) {
        try {
            return ordertempMapper.updateOrdertemp(ordertemp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int checkStore(Integer id) {
        String key = "curriculum:"+id;
        if(redisUtil.hasKey(key)){
            String string = redisUtil.get(key).toString();
            Curriculum curriculum = JSON.parseObject(string, Curriculum.class);
            if(curriculum.getBanrong()>0){
                return 1;
            }
        }
        return 0;
    }

    @Override
    public int lockRoomStock(Integer id, Integer uid) {
        //获取数据
        String key = "curriculum:"+id;
        String string = redisUtil.get(key).toString();
        Curriculum curriculum = JSON.parseObject(string, Curriculum.class);
        //创建临时库存信息
        Ordertemp ordertemp = new Ordertemp();
        ordertemp.setGoodsId(id);
        ordertemp.setStore(curriculum.getBanrong());
        ordertemp.setStatus(0);
        ordertemp.setUserId(uid);
        //修改redis库存信息
        curriculum.setBanrong(curriculum.getBanrong()-1);
        //重新写入到redis
        redisUtil.set(key,JSON.toJSONString(curriculum));
        try {
            return ordertempMapper.insertOrdertemp(ordertemp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public String qgWhile(Integer subjectId) {
        int i = this.checkStore(subjectId);
        String qgkey = "qg:"+subjectId+":"+1;
        if(redisUtil.hasKey(qgkey)){
            return "success";
        }else{
            if(i<=0){
                return "none";
            }else{
                return "input";
            }
        }
    }
}
