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
 * @className UserModifyProducer
 * @description 用户修改生产者
 * @date 2022/4/15 13:53
 */
@Slf4j
@Component
public class UserModifyProducer {

    @Autowired
    private StreamBridge streamBridge;

    /**
     * 发送用户信息修改事件
     *
     * @param userModifyMessage
     */
    public void send(UserModifyMessage userModifyMessage) {

        Message<UserModifyMessage> message = MessageBuilder.withPayload(userModifyMessage)
                .setHeader(MqConstant.COM_TYPE, "userModify")
                .setHeader(MqConstant.COM_UID, UUID.randomUUID().toString())
                .setHeader(MqConstant.COM_BUSINESS_NO, userModifyMessage.getUserNo())
                .setHeader(MqConstant.ROCKET_TAGS, "userModify")
                .setHeader(MqConstant.ROCKET_KEYS, userModifyMessage.getUserNo())
                .build();

        log.info(">>>>>收到用户信息修改的消息,开始发送该事件.{},uid:{}", userModifyMessage, message.getHeaders().get("uid"));

        streamBridge.send("user-server-event", message);
    }
}
