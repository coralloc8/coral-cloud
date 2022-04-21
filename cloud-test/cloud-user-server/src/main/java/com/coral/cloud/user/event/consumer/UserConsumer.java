package com.coral.cloud.user.event.consumer;

import com.coral.cloud.user.common.constants.MqConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

/**
 * @author huss
 * @version 1.0
 * @className UserModifyConsumer
 * @description 用户信息修改消费者
 * @date 2022/4/15 14:00
 */
@Slf4j
@Configuration
public class UserConsumer {

    /**
     * 用户信息更新
     *
     * @return
     */
    @Bean
    public Consumer<Message<UserModifyMessage>> userModify() {
        return message -> {
            log.info(">>>>>收到用户信息修改的消息，开始消费，消息uid:{}，业务编码为:{},message:{}",
                    message.getHeaders().get(MqConstant.COM_UID), message.getHeaders().get(MqConstant.COM_BUSINESS_NO), message);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info(">>>>>消费完成.");
        };
    }

    /**
     * 用户信息创建
     *
     * @return
     */
    @Bean
    public Consumer<Message<UserModifyMessage>> userCreate() {
        return message -> {
            log.info(">>>>>收到用户信息创建的消息，开始消费.{}", message.toString());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info(">>>>>消费完成.");
        };
    }

}
