package com.moon.moon_api.mq.provider;

import com.moon.constants.RabbitConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @ClassName PushOrderDataProvider
 * @Description: TODO
 * @Author zyl
 * @Date 2020/10/28
 * @Version V1.0
 **/
@Component
@Slf4j
public class PushOrderDataProvider {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMsg(String msg) {
        log.info("PushOrderDataProvider ,msg:{} ,", msg);
        rabbitTemplate.convertAndSend(RabbitConstant.DD_ORDER_QUEUE, RabbitConstant.DD_ORDER_QUEUE, msg, message -> {
                    message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                    return message;
                },
                new CorrelationData(UUID.randomUUID().toString()));
    }

}
