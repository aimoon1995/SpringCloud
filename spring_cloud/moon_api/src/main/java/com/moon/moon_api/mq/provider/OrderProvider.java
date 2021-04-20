package com.moon.moon_api.mq.provider;

import com.moon.constants.RabbitConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

/**
 * @ClassName OrderProvider
 * @Description: 将消息发送到 dd.order.pool.exchange ---》dd.order.pool.queue
 * @Author zyl
 * @Date 2020/8/5
 * @Version V1.0
 **/
@Component
@Slf4j
public class OrderProvider {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMsg(Map<String, Object> map) {
        rabbitTemplate.convertAndSend(RabbitConstant.DD_ORDER_POOL_EXCHANGE, RabbitConstant.DD_ORDER_POOL_QUEUE, map, message -> {
                    message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                    return message;
                },
                new CorrelationData(UUID.randomUUID().toString()));
    }

}


