package com.zb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author 王淑婷
 * @Description TODO
 * @Date 2020/6/3
 * @Version V1.0
 */
@SpringBootApplication
@EnableEurekaServer
public class MyEurekaServerApp {
    public static void main(String[] args) {
        SpringApplication.run(MyEurekaServerApp.class,args);
    }
}
