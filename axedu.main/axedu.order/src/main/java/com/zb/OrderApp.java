package com.zb;

import com.zb.service.OrderTempService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author 王淑婷
 * @Description TODO
 * @Date 2020/6/4
 * @Version V1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableScheduling
public class OrderApp {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(OrderApp.class, args);
        OrderTempService bean = context.getBean(OrderTempService.class);
        bean.CurriculumToRedis();
    }
}
