package com.coral.web.websocket.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author huss
 * @version 1.0
 * @className UserInterceptor
 * @description 用户拦截
 * @date 2022/10/28 14:59
 */
@Slf4j
public class UserInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            Object raw = message
                    .getHeaders()
                    .get(SimpMessageHeaderAccessor.NATIVE_HEADERS);
            log.debug(">>>>>header:{}", raw);
            if (raw instanceof Map) {
                Object name = ((Map) raw).get("userId");

                if (name instanceof List) {
                    accessor.setUser(new User(((List<?>) name).get(0).toString()));
                } else {
                    accessor.setUser(new User(name.toString()));
                }
            }
        }
        log.info(">>>>>message:{}", message);
        return message;
    }
}
