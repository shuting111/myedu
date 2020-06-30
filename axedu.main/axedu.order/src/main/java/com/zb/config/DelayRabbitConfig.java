package com.zb.config;



import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 王淑婷
 * @Description TODO
 * @Date 2020/5/3
 * @Version V1.0
 */
@Configuration
public class DelayRabbitConfig {




    /**
     * DLX，dead letter发送到的 exchange
     * 延时消息就是发送到该交换机的
     */
    public static final String ORDER_QGDELAY_EXCHANGE = "user.order.delay.exchange";
    /**
     * routing key 名称
     * 具体消息发送在该 routingKey 的
     */
    public static final String ORDER_QGDELAY_ROUTING_KEY = "order_delay";

    /**
     * 延迟队列 TTL 名称
     */
    public static final String ORDER_QGDELAY_QUEUE = "user.order.delay.queue";

    public static final String ORDER_QGQUEUE_NAME = "user.order.queue";
    public static final String ORDER_QGEXCHANGE_NAME = "user.order.exchange";
    public static final String ORDER_QGROUTING_KEY = "order";

    /**
     * 死信队列配置
     * <p>
     * 1、params.put("x-message-ttl", 5 * 1000);
     * 第一种方式是直接设置 Queue 延迟时间 但如果直接给队列设置过期时间,这种做法不是很灵活,（当然二者是兼容的,默认是时间小的优先）
     * 2、rabbitTemplate.convertAndSend(book, message -> {
     * message.getMessageProperties().setExpiration(2 * 1000 + "");
     * return message;
     * });
     * 第二种就是每次发送消息动态设置延迟时间,这样我们可以灵活控制
     **/
    @Bean
    public Queue delayQGOrderQueue() {
        Map<String, Object> params = new HashMap<String, Object >();
        // x-dead-letter-exchange 声明了队列里的死信转发到的DLX名称，
        params.put("x-dead-letter-exchange", ORDER_QGEXCHANGE_NAME);
        // x-dead-letter-routing-key 声明了这些死信在转发时携带的 routing-key 名称。
        params.put("x-dead-letter-routing-key", ORDER_QGROUTING_KEY);
        return new Queue(ORDER_QGDELAY_QUEUE, true, false, false, params);
    }
    /**
     * 需要将一个队列绑定到交换机上，要求该消息与一个特定的路由键完全匹配。
     * 这是一个完整的匹配。如果一个队列绑定到该交换机上要求路由键 “dog”，则只有被标记为“dog”的消息才被转发，
     * 不会转发dog.puppy，也不会转发dog.guard，只会转发dog。
     * @return DirectExchange
     *
     * 死信交换机
     */
    @Bean
    public DirectExchange orderQGDelayExchange() {
        return new DirectExchange(ORDER_QGDELAY_EXCHANGE);
    }

    /**
     *给死信队列绑定交换机
     * @return
     */
    @Bean
    public Binding dlxBinding() {
        return BindingBuilder.bind(delayQGOrderQueue()).to(orderQGDelayExchange()).with(ORDER_QGDELAY_ROUTING_KEY);
    }

    /**
     * 死信接收队列
     * @return
     */
    @Bean
    public Queue orderQGQueue() {
        return new Queue(ORDER_QGQUEUE_NAME, true);
    }
    /**
     * 将路由键和某模式进行匹配。此时队列需要绑定要一个模式上。
     * 符号“#”匹配一个或多个词，符号“*”匹配不多不少一个词。因此“audit.#”能够匹配到“audit.irs.corporate”，但是“audit.*” 只会匹配到“audit.irs”。
     *
     * 死信接收交换机
     **/
    @Bean
    public TopicExchange orderQGTopicExchange() {
        return new TopicExchange(ORDER_QGEXCHANGE_NAME);
    }

    /**
     * 死信交换机绑定消费队列
     * @return
     */
    @Bean
    public Binding orderQGBinding() {
        // TODO 如果要让延迟队列之间有关联,这里的 routingKey 和 绑定的交换机很关键
        return BindingBuilder.bind(orderQGQueue()).to(orderQGTopicExchange()).with(ORDER_QGROUTING_KEY);
    }

}
