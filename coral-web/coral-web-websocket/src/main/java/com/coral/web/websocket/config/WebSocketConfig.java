package com.coral.web.websocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @author huss
 * @version 1.0
 * @className WebSocketConfig
 * @description websocket配置
 * @date 2022/10/27 14:04
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * 注册stomp的端点
     * 注册一个STOMP协议的节点，并映射到指定的URL
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //endPoint 注册协议节点,并映射指定的URl点对点-用
        //注册一个名字为"/ws" 的endpoint,并指定 SockJS协议。
        registry.addEndpoint("/ws") //连接前缀
                .setAllowedOriginPatterns("*")//跨域处理
                .withSockJS();//支持socketJs
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //订阅Broker名称:topic 代表发布广播，即群发
        registry.enableSimpleBroker("/topic", "/queue");
        //send命令时需要带上/app前缀
        // 全局使用的消息前缀（客户端订阅路径上会体现出来）
        registry.setApplicationDestinationPrefixes("/app");

        //修改convertAndSendToUser方法前缀
        //点对点使用的订阅前缀（客户端订阅路径上会体现出来），
        // 不设置的话，默认也是/user/
        registry.setUserDestinationPrefix("/user/");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new UserInterceptor());
    }
}
