package com.zb.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 王淑婷
 * @Description TODO
 * @Date 2020/4/28
 * @Version V1.0
 */
@Configuration
public class RabbitConfig {
    public static final String QUEUE_room="curriculum_Queue";
    public static final String EXCHANGE_TOPIC_INFORM="exchange_curriculum";

    @Bean(EXCHANGE_TOPIC_INFORM)
    public Exchange createExchange(){
        return ExchangeBuilder.topicExchange(EXCHANGE_TOPIC_INFORM).durable(true).build();
    }

    @Bean(QUEUE_room)
    public Queue createEmailQueue(){
        Queue queue =new Queue(QUEUE_room);
        return queue;
    }


    @Bean
    public Binding bindingEmail(@Qualifier(EXCHANGE_TOPIC_INFORM) Exchange exchange , @Qualifier(QUEUE_room) Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with("inform.#.curriculum.#").noargs();
    }

}
