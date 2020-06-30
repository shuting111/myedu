package com.zb.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EsConfig {
    @Value("${myes.host}")//对应application里的配置
    private String hostList;

    //创建客户端对象
    @Bean
    public RestHighLevelClient createClient(){
        //配件集群环境的代码
        String [] hosts = hostList.split(",");
        HttpHost [] httpHosts=new HttpHost[hosts.length];
        for (int i = 0; i < hosts.length; i++) {
            String item  = hosts[i];
            httpHosts[i]=new HttpHost(item.split(":")[0],Integer.parseInt(item.split(":")[1]));
        }
        return new RestHighLevelClient(RestClient.builder(httpHosts));
    }
}
