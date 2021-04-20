package com.moon.moon_api.mq.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DdRealTimePositionConsumer {


    @RabbitListener(queues = "realtime_location_sync", containerFactory = "rabbitListenerContainerFactory")
    public void onMessage(Message message, Channel channel) throws Exception {
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        String realtimeLocation = new String(message.getBody());
    }
}
