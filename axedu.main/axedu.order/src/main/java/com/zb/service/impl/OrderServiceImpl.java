package com.zb.service.impl;

import com.alibaba.fastjson.JSON;
import com.zb.config.DelayRabbitConfig;
import com.zb.config.DelayRabbitTempConfig;
import com.zb.config.RabbitOrderConfig;
import com.zb.mapper.OrdersMapper;
import com.zb.pojo.Curriculum;
import com.zb.pojo.Orders;
import com.zb.pojo.Ordertemp;
import com.zb.pojo.Task;
import com.zb.service.OrderService;
import com.rabbitmq.client.Channel;
import com.zb.service.OrderTempService;
import com.zb.service.RegisterService;
import com.zb.service.TaskService;
import com.zb.util.IdWorker;
import com.zb.util.RedisUtil;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private OrderTempService orderTempService;
    @Autowired(required = false)
    private OrdersMapper ordersMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RegisterService registerService;

    @Override
    public Integer insertOrders(Orders orders) {
        try {
            return ordersMapper.insertOrders(orders);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    //分布式锁
    //高并发下的 超时问题 time out error
    //将请求压入mq中，每一个请求立即返回结果
    @Override
    public String qgCurriculum(Integer subjectId, String token) {
        Map<String,Object> param = new HashMap<>();
        param.put("subjectId",subjectId);
        param.put("token",token);
        amqpTemplate.convertAndSend(RabbitOrderConfig.EXCHANGE_ORDER_INFORM,"inform.qg",param);
        return "正在排队中，请等待...";
    }

    @Override
    public Integer insertQgOrders(Orders orders) {
        String orderNo = orders.getOrderNo();
        amqpTemplate.convertAndSend(DelayRabbitConfig.ORDER_QGDELAY_EXCHANGE,DelayRabbitConfig.ORDER_QGDELAY_ROUTING_KEY, orderNo, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setExpiration(1*1000*60+"");
                return message;
            }
        });
        try {
            return ordersMapper.insertOrders(orders);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Orders findByOrderNo(String orderNo) {
        return ordersMapper.findByOrderNo(orderNo);
    }

    @Override
    public Integer updateOrders(Orders orders) {
        try {
            return ordersMapper.updateOrders(orders);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Integer updateSuccessOrders(Orders orders) {
        Orders order = this.findByOrderNo(orders.getOrderNo());
        Ordertemp ordertemp = orderTempService.findOnlyOne(order.getGoodsId(), order.getUserId());
        if(order.getOrderStatus()==0&&ordertemp.getStatus()==0&&ordertemp.getStatus()!=null){
            Orders orderNo = this.findByOrderNo(orders.getOrderNo());
            orderNo.setOrderStatus(2);
            Integer num = this.updateOrders(orderNo);
            ordertemp.setStatus(2);
            num+=orderTempService.updateOrdertemp(ordertemp);
            if(num==2){
                Task task = new Task();
                task.setVersion(1);
                task.setStatus(1);
                task.setMyExchange("ex_learning_addchoosecourse");
                task.setMyRoutingKey("addchoosecourse");
                task.setTaskId(IdWorker.getId());
                Map<String,Object>param = new HashMap<>();
                param.put("goodsId",order.getGoodsId());
                param.put("userId",order.getUserId());
                param.put("price",order.getPayAmount());
                param.put("valid",1);
                param.put("startTime",order.getStartTime());
                param.put("endTime",order.getEndTime());
                param.put("status",order.getOrderStatus());
                task.setRequestBody(JSON.toJSONString(param));
                return taskService.insertTask(task);
            }
        }
        return 0;
    }

    @RabbitListener(queues = DelayRabbitConfig.ORDER_QGQUEUE_NAME)
    public void revice(String orderNo,Message message, Channel channel){
        Orders orders = this.findByOrderNo(orderNo);
        if(orders.getOrderStatus()==0){
            orders.setOrderStatus(1);
            try {
                this.updateOrders(orders);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @RabbitListener(queues = RabbitOrderConfig.QUEUE_QG)
    public void reviceMq(Map<String,Object>param, Message message, Channel channel){
        String token = (String) param.get("token");
        Integer subjectId =(Integer) param.get("subjectId");
        System.out.println(subjectId+"\t"+token);
        String key = "lock"+subjectId;
        try {
            while(!redisUtil.lock(key)){
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            int i = orderTempService.checkStore(subjectId);
            if(i<0){
                System.out.println("该课程名额已满");
                return;
            }
            String qgkey = "qg:"+subjectId+":"+1;
            int stock = orderTempService.lockRoomStock(subjectId, 1);
            if(stock>0){
                redisUtil.hmset(qgkey,param,60*5);
                Map<String,Object>delayData = new HashMap<>();
                delayData.put("uid",1);
                delayData.put("subjectId",subjectId);
                amqpTemplate.convertAndSend(DelayRabbitTempConfig.ORDER_DELAY_EXCHANGE, DelayRabbitTempConfig.ORDER_DELAY_ROUTING_KEY, delayData, new MessagePostProcessor() {
                    @Override
                    public Message postProcessMessage(Message message) throws AmqpException {
                        message.getMessageProperties().setExpiration(2*60*1000+"");
                        return message;
                    }
                });
                System.out.println("预定成功");
                return ;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            redisUtil.unlock(key);
        }
        System.out.println(-1);
        return ;
    }

    @RabbitListener(queues = DelayRabbitTempConfig.ORDER_QUEUE_NAME)
    public void receiveQgMessage(Map<String,Object>param,Message message, Channel channel){
        Integer uid = (Integer) param.get("uid");
        Integer subjectId = (Integer) param.get("subjectId");
        System.out.println(uid+"\t"+subjectId);
        Ordertemp onlyOne = orderTempService.findOnlyOne(subjectId, uid);
        if(onlyOne.getStatus()==0){
            String key = "curriculum:"+subjectId;
            if(redisUtil.hasKey(key)){
                String string = redisUtil.get(key).toString();
                Curriculum curriculum = JSON.parseObject(string, Curriculum.class);
                curriculum.setBanrong(curriculum.getBanrong()+1);
                redisUtil.set(key,JSON.toJSONString(curriculum));
                onlyOne.setStatus(1);
                orderTempService.updateOrdertemp(onlyOne);
            }
        }
    }
}
