package com.moon.moon_api.mq.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @ClassName OrderConsumer
 * @Description: 将订单数据状态同步给滴滴
 * @Author zyl
 * @Date 2020/8/6
 * @Version V1.0
 **/
@Component
@Slf4j
public class OrderConsumer {

    /**
     * 处理滴滴订单的各种流程变更操作
     *
     * @param message
     * @param channel
     * @throws Exception
     * @author zyl
     */
    @RabbitListener(queues = "dd_order", containerFactory = "rabbitListenerContainerFactory")
    public void onMessage(Message message, Channel channel) {
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            String orderInfo = new String(message.getBody());

        } catch (Exception e) {
            e.printStackTrace();
            log.error("处理滴滴订单的各种事件异常", e);
        }

    }


}
