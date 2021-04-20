/**
 * @Description:
 * @Author: ywang
 * @Date: 2019/5/21 8:26 PM
 */

package com.moon.moon_api.config;


import com.moon.constants.RabbitConstant;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 定义交换机和队列
 * ddOrderPoolExchange --->  ddOrderPoolQueue  当创建成单成功后将消息发送到此队列
 * ddOrderMatchTtlExchange ---> ddOrderMatchTtlQueue  将绑定的司机信息发送到此队列
 * ddOrderMatchExchange ---> ddOrderMatchQueue
 *
 * @author zyl
 */
@Configuration
public class RabbitConfig {

    /**
     * 创建订单后将消息发送到此交换机
     *
     * @Author: zyl
     */
    @Bean(value = "ddOrderExchange")
    DirectExchange ddOrderExchange() {
        return (DirectExchange) ExchangeBuilder
                .directExchange(RabbitConstant.DD_ORDER_EXCHANGE)
                .durable(true)
                .build();
    }

    /**
     * 快车订单流程中有需要同步到滴滴的在api端，会将消息发送到该队列
     *
     * @return
     */
    @Bean(value = "ddOrderQueue")
    public Queue ddOrderQueue() {
        return new Queue(RabbitConstant.DD_ORDER_QUEUE, true, false, false);
    }

    /**
     * 出租车订单流程中有需要同步到滴滴的在api端，会将消息发送到该队列
     *
     * @return
     */
    @Bean(value = "ddTaxiOrderQueue")
    public Queue ddTaxiOrderQueue() {
        return new Queue(RabbitConstant.DD_TAXI_ORDER_QUEUE, true, false, false);
    }
    /**
     * 队列和交换机的绑定-routekey
     *
     * @param ddOrderExchange 消息中心交换机
     * @param ddOrderQueue    消息中心队列
     * @return
     */
    @Bean
    Binding ddOrderBinding(@Qualifier("ddOrderExchange") DirectExchange ddOrderExchange,
                               @Qualifier("ddOrderQueue") Queue ddOrderQueue) {
        return BindingBuilder
                .bind(ddOrderQueue)
                .to(ddOrderExchange)
                .with(RabbitConstant.DD_ORDER_QUEUE);
    }

    /**
     * 队列和交换机的绑定-routekey
     *
     * @param ddOrderExchange 消息中心交换机
     * @param ddTaxiOrderQueue    消息中心队列
     * @return
     */
    @Bean
    Binding ddTaxiOrdeBinding(@Qualifier("ddOrderExchange") DirectExchange ddOrderExchange,
                          @Qualifier("ddTaxiOrderQueue") Queue ddTaxiOrderQueue) {
        return BindingBuilder
                .bind(ddTaxiOrderQueue)
                .to(ddOrderExchange)
                .with(RabbitConstant.DD_TAXI_ORDER_QUEUE);
    }

    /**
     * @MethodName: ddOrderPoolQueue
     * @Description:
     * durable:是否持久化,默认是false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
     * exclusive:默认也是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
     * autoDelete:是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。
     * return new Queue("TestDirectQueue",true,false,false);
     * @Param: []
     * @Return: org.springframework.amqp.core.Queue
     * @Author: zyl
     * @Date: 2020/7/10
     **/
    /**
     * 创建订单后将消息发送到此交换机
     *
     * @Author: zyl
     */
    @Bean(value = "ddOrderPoolExchange")
    DirectExchange ddOrderPoolExchange() {
        return (DirectExchange) ExchangeBuilder
                .directExchange(RabbitConstant.DD_ORDER_POOL_EXCHANGE)
                .durable(true)
                .build();
    }

    /**
     * 创建订单后将消息发送到此队列
     *
     * @return
     */
    @Bean(value = "ddOrderPoolQueue")
    public Queue ddOrderPoolQueue() {
        return new Queue(RabbitConstant.DD_ORDER_POOL_QUEUE, true, false, false);
    }


    /**
     * 队列和交换机的绑定-routekey
     *
     * @param ddOrderPoolExchange 消息中心交换机
     * @param ddOrderPoolQueue    消息中心队列
     * @return
     */
    @Bean
    Binding ddOrderPoolBinding(@Qualifier("ddOrderPoolExchange") DirectExchange ddOrderPoolExchange,
                               @Qualifier("ddOrderPoolQueue") Queue ddOrderPoolQueue) {
        return BindingBuilder
                .bind(ddOrderPoolQueue)
                .to(ddOrderPoolExchange)
                .with(RabbitConstant.DD_ORDER_POOL_QUEUE);
    }


    /**
     * 交换机名称 --将绑定的司机信息延时转置发送到此交换机
     *
     * @Author: zyl
     */
    @Bean(value = "ddOrderMatchExchange")
    DirectExchange ddOrderMatchExchange() {
        return (DirectExchange) ExchangeBuilder
                .directExchange(RabbitConstant.DD_ORDER_MATCH_EXCHANGE)
                .durable(true)
                .build();
    }

    /**
     * 消息通知队列名称--将绑定的司机信息延时转置发送到此队列
     *
     * @return
     */
    @Bean("ddOrderMatchQueue")
    public Queue ddOrderMatchQueue() {
        return new Queue(RabbitConstant.DD_ORDER_MATCH_QUEUE, true, false, false);
    }

    /**
     * 队列和交换机的绑定-routekey
     *
     * @param ddOrderMatchExchange 消息中心交换机
     * @param ddOrderMatchQueue    消息中心队列
     * @return
     */
    @Bean
    Binding ddOrderMatchBinding(@Qualifier("ddOrderMatchExchange") DirectExchange ddOrderMatchExchange,
                                @Qualifier("ddOrderMatchQueue") Queue ddOrderMatchQueue) {
        return BindingBuilder
                .bind(ddOrderMatchQueue)
                .to(ddOrderMatchExchange)
                .with(RabbitConstant.DD_ORDER_MATCH_QUEUE);
    }


    /**
     * ttl(延时)交换机名称 --将绑定的司机信息发送到此交换机
     *
     * @Author: zyl
     */
    @Bean(value = "ddOrderMatchTtlExchange")
    DirectExchange ddOrderMatchTtlExchange() {
        return (DirectExchange) ExchangeBuilder
                .directExchange(RabbitConstant.DD_ORDER_MATCH_TTL_EXCHANGE)
                .durable(true)
                .build();
    }


    /**
     * 创建订单后将消息发送到此队列
     *
     * @return
     */
    @Bean(value = "ddOrderMatchTtlQueue")
    public Queue ddOrderMatchTtlQueue() {
        // 配置到期后转发的交换
        return QueueBuilder
                .durable(RabbitConstant.DD_ORDER_MATCH_TTL_QUEUE)
                .withArgument("x-dead-letter-exchange", RabbitConstant.DD_ORDER_MATCH_EXCHANGE)
                // 配置到期后转发的路由键
                .withArgument("x-dead-letter-routing-key", RabbitConstant.DD_ORDER_MATCH_QUEUE)
                .build();
    }


    /**
     * 队列和交换机的绑定-routekey
     *
     * @param ddOrderMatchTtlExchange 消息中心交换机
     * @param ddOrderMatchTtlQueue    消息中心队列
     * @return
     */
    @Bean
    Binding ddOrderMatchTtlBinding(@Qualifier("ddOrderMatchTtlExchange") DirectExchange ddOrderMatchTtlExchange,
                                   @Qualifier("ddOrderMatchTtlQueue") Queue ddOrderMatchTtlQueue) {
        return BindingBuilder
                .bind(ddOrderMatchTtlQueue)
                .to(ddOrderMatchTtlExchange)
                .with(RabbitConstant.DD_ORDER_MATCH_TTL_QUEUE);
    }

    /**
     * 创建订单后将消息发送到此交换机
     *
     * @Author: wn
     */
    @Bean(value = "ddDataSyncExchange")
    DirectExchange ddDataSyncExchange() {
        return (DirectExchange) ExchangeBuilder
                .directExchange(RabbitConstant.DD_DATA_SYNC_EXCHANGE)
                .durable(true)
                .build();
    }


    /**
     * 消息通知队列名称--将绑定的司机信息延时转置发送到此队列
     *
     * @Author: wn
     * @return
     */
    @Bean(value = "ddRealTimeFeeQueue")
    public Queue ddRealTimeFeeQueue() {
        return new Queue(RabbitConstant.DD_REALTIME_FEE_QUEUE, true, false, false);
    }

    /**
     * 消息通知队列名称--将绑定的司机信息发送到此队列
     *
     * @Author: wn
     * @return
     */
    @Bean(value = "ddRealTimePositionQueue")
    public Queue ddRealTimePositionQueue() {
        return new Queue(RabbitConstant.DD_REALTIME_POSITION_QUEUE, true, false, false);
    }

    /**
     * 队列和交换机的绑定-routekey
     *
     * @param ddDataSyncExchange 消息中心交换机
     * @param ddRealTimeFeeQueue 消息中心队列
     * @return
     */
    @Bean
    Binding ddRealTimeFeeBinding(@Qualifier("ddDataSyncExchange") DirectExchange ddDataSyncExchange,
                                   @Qualifier("ddRealTimeFeeQueue") Queue ddRealTimeFeeQueue) {
        return BindingBuilder
                .bind(ddRealTimeFeeQueue)
                .to(ddDataSyncExchange)
                .with(RabbitConstant.DD_REALTIME_FEE_QUEUE);
    }

    /**
     * 队列和交换机的绑定-routekey
     *
     * @param ddDataSyncExchange 消息中心交换机
     * @param ddRealTimePositionQueue    消息中心队列
     * @return
     */
    @Bean
    Binding ddRealTimePositionBinding(@Qualifier("ddDataSyncExchange") DirectExchange ddDataSyncExchange,
                                   @Qualifier("ddRealTimePositionQueue") Queue ddRealTimePositionQueue) {
        return BindingBuilder
                .bind(ddRealTimePositionQueue)
                .to(ddDataSyncExchange)
                .with(RabbitConstant.DD_REALTIME_POSITION_QUEUE);
    }


    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(new Jackson2JsonMessageConverter());
        return template;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }
}
