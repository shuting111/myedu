package com.zb.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitOrderConfig {
    public static final String QUEUE_QG="qg_Queue";
    public static final String EXCHANGE_ORDER_INFORM="exchange_qg";

    @Bean(EXCHANGE_ORDER_INFORM)
    public Exchange createOrderExchange(){
        return ExchangeBuilder.topicExchange(EXCHANGE_ORDER_INFORM).durable(true).build();
    }

    @Bean(QUEUE_QG)
    public Queue createOrderQueue(){
        Queue queue =new Queue(QUEUE_QG);
        return queue;
    }

    @Bean
    public Binding bindingOrder(@Qualifier(EXCHANGE_ORDER_INFORM) Exchange exchange , @Qualifier(QUEUE_QG) Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with("inform.#.qg.#").noargs();
    }

}
