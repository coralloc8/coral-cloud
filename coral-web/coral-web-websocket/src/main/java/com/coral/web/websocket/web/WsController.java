package com.coral.web.websocket.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

/**
 * @author huss
 * @version 1.0
 * @className WsController
 * @description ws控制器
 * @date 2022/10/27 14:26
 */
@Slf4j
@RestController
public class WsController {
    @Autowired
    private SimpMessageSendingOperations sendingOperations;

    @Autowired
    private SimpUserRegistry simpUserRegistry;

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(@Header String userId, @Payload HelloMessage message) throws Exception {
        log.info(">>>>>>>message:{},userId:{}", message, userId);
        Thread.sleep(2000); // simulated delay
        return new Greeting("Hello All, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }

    @MessageMapping("/send")
    public void sendToUser(@Payload HelloMessage message) throws Exception {
        Thread.sleep(2000); // simulated delay
        log.info(">>>>all users:{}", simpUserRegistry.getUsers());
        log.info("userId:{},msg:{}", message.getUserId(), message.getName());
        sendingOperations.convertAndSendToUser(message.getUserId(), "/queue/msg",
                new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!"));
    }

    @PostMapping("/send2")
    public String sendToUser2(@RequestBody HelloMessage message) throws Exception {
        log.info("userId:{},msg:{}", message.getUserId(), message.getName());
        sendingOperations.convertAndSendToUser(message.getUserId(), "/queue/msg",
                new Greeting("click url"));

        return "success";
    }


    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Greeting {
        private String content;
    }

    @Data
    public static class HelloMessage {
        private String userId;
        private String name;
    }
}
