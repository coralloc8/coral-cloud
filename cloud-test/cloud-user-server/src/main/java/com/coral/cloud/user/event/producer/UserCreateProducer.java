package com.coral.cloud.user.event.producer;

import com.coral.cloud.user.common.constants.MqConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author huss
 * @version 1.0
 * @className UserCreateProducer
 * @description 用户创建生产者
 * @date 2022/4/15 13:53
 */
@Slf4j
@Component
public class UserCreateProducer {

    @Autowired
    private StreamBridge streamBridge;

    /**
     * 发送用户信息创建事件
     *
     * @param userModifyMessage
     */
    public void send(UserModifyMessage userModifyMessage) {
        log.info(">>>>>收到用户信息创建的消息,开始发送该事件.{}", userModifyMessage);

        Message<UserModifyMessage> message = MessageBuilder.withPayload(userModifyMessage)
                .setHeader(MqConstant.COM_TYPE, "userCreate")
                .setHeader(MqConstant.COM_UID, UUID.randomUUID().toString())
                .setHeader(MqConstant.COM_BUSINESS_NO, userModifyMessage.getUserNo())
                .setHeader(MqConstant.ROCKET_TAGS, "userCreate")
                .setHeader(MqConstant.ROCKET_KEYS, userModifyMessage.getUserNo())
                .setHeader(MqConstant.ROCKET_DELAY_TIME_LEVEL, 4)
                .setHeader(MqConstant.RABBIT_DELAY, 30 * 1000)
                .build();

        streamBridge.send("user-server-event", message);
    }
}
