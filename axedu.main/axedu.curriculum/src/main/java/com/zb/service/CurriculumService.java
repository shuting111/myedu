package com.zb.service;

import com.rabbitmq.client.Channel;
import com.zb.form.CurriArgs;
import com.zb.pojo.Advert;
import com.zb.pojo.Curriculum;
import com.zb.util.PageUtil;
import org.springframework.amqp.core.Message;

import java.util.List;
import java.util.Map;

/**
 * @author 王淑婷
 * @Description TODO
 * @Date 2020/6/8
 * @Version V1.0
 */
public interface CurriculumService {
    //将课程全部存入redis
    public void CurriculmToRedis();
    //根据id修改redis里的点击量
    public void IncreaseHits(Integer id);
    //查询猜你喜欢商品
    public List<Curriculum> findLike();
    //维一查询
    public Curriculum findCurriculumById(Integer id);
    //查询精品课程
    public List<Curriculum> findIsChoiceness(Integer isChoiceness);
    //查询全部课程
    public List<Curriculum> findCurricilumAll();
    //es查询
    public PageUtil<Curriculum> findKeyWord(CurriArgs c)throws Exception;

    //添加课程
    public int insertCurriculum(Curriculum curriculum);
    //监听点击量的MQ
    public void reciveHits(Map<String,Object> param, Message message, Channel channel);
    //猜你喜欢定时函数
    public void guessLike();

    /**
     * 查询所有优惠课程
     */
    public List<Curriculum>findIsDiscount();






}
