package com.moon.moon_api.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DdRealTimeFeeConsumer {

    @RabbitListener(queues = "realtime_fee_sync", containerFactory = "rabbitListenerContainerFactory")
    public void onMessage(Message message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws Exception {
        channel.basicAck(tag, false);
        String realTimeFee = new String(message.getBody());
        JSONObject jsonObject = JSONObject.parseObject(realTimeFee);
    }
}
