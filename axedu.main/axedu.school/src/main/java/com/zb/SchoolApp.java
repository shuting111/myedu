package com.zb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * @author 王淑婷
 * @Description TODO
 * @Date 2020/6/4
 * @Version V1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class SchoolApp {
    //jjjjjjjjjkkkksss
    public static void main(String[] args) {
        SpringApplication.run(SchoolApp.class,args);
    }
}
