package com.zb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author 王淑婷
 * @Description TODO
 * @Date 2020/6/1
 * @Version V1.0
 */
@SpringBootApplication
@EnableZuulProxy
public class GatewayServerApp {
    public static void main(String[] args) {
        SpringApplication.run(GatewayServerApp.class,args);
    }
}
