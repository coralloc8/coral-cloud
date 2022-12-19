package com.coral.test.spring.event.web;

import com.coral.test.spring.event.domain.UserChanged;
import com.coral.test.spring.event.domain.UserCreated;
import com.coral.test.spring.event.listener.BaseEvent;
import com.coral.web.core.response.Result;
import com.coral.web.core.response.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author huss
 * @version 1.0
 * @className TestController
 * @description todo
 * @date 2022/12/10 16:34
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    private static Map<String, SseEmitter> SSE_CACHE = new ConcurrentHashMap<>();


    @GetMapping
    public Result<Void> send() {
        applicationEventPublisher.publishEvent(BaseEvent.createEvent(new UserChanged("001", "hus")));
        applicationEventPublisher.publishEvent(BaseEvent.createEvent(UserCreated.builder()
                .userId("002")
                .username("hu002")
                .sex("女")
                .age(23)
                .build()));

        return new Results().success();
    }

    @PostMapping("/watch/{clientId}")
    public Result<String> watch(@PathVariable String clientId) {
        //1分钟超时
        SseEmitter sseEmitter = SSE_CACHE.computeIfAbsent(clientId, c -> {
            SseEmitter emitter = new SseEmitter(60000L);
            emitter.onTimeout(() -> SSE_CACHE.remove(c));
            emitter.onCompletion(() -> System.out.println("发送完成!"));
            emitter.onError(error -> System.out.println("系统错误!" + error));
            return emitter;
        });


        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(3000);
//                sseEmitter.send(SseEmitter.event().reconnectTime(1000).data("连接成功"));
                sseEmitter.send("当前时间:" + System.currentTimeMillis());
            } catch (InterruptedException | IOException e) {
                throw new RuntimeException(e);
            }
        }

        return new Results().success();
    }
}
