package com.example.spring.simple.web1.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * @description: sse测试
 * @author: huss
 * @time: 2020/11/9 16:57
 */
@Slf4j
@RestController
@RequestMapping("/sse")
public class SseController {

    /**
     * 结束标志
     */
    private static final String FINISHED = "finished";

    /**
     * SseEmitter对象存储
     */
    private static final Map<String, SseEmitter> SSE_EMITTER_MAP = new ConcurrentHashMap<>();

    /**
     * 已使用的数字
     */
    private static final Map<String, List<String>> USED_NUMBER_MAP = new ConcurrentHashMap<>();

    /**
     * 所有消息集合
     */
    private static final List<MessageInfo> MESSAGE_INFOS = MessageInfo.createMessages();

    @Autowired
    private TaskExecutor taskExecutor;

    /**
     * 查看所有消息集合
     * 
     * @return
     */
    @GetMapping("list")
    public Result list() {
        int allCount = MESSAGE_INFOS.size();
        MESSAGE_INFOS.forEach(message -> {
            int status =
                USED_NUMBER_MAP.getOrDefault(String.valueOf(message.getId()), Collections.emptyList()).size() < allCount
                    ? 1 : 2;
            message.setStatus(status);
        });
        return Result.success(MESSAGE_INFOS);
    }

    @GetMapping("/{id}/details")
    public Result details(@PathVariable Long id) {
        long allCount = MESSAGE_INFOS.size();
        List<String> usedNumbers = USED_NUMBER_MAP.getOrDefault(String.valueOf(id), Collections.emptyList());
        long usedCount = usedNumbers.size();
        List<String> allNumbers =
            MESSAGE_INFOS.parallelStream().map(MessageInfo::getNumber).collect(Collectors.toList());
        // 删除所有已使用的
        allNumbers.removeAll(usedNumbers);

        UnUsedInfo unUsedInfo = new UnUsedInfo(id, allCount, usedCount, (allCount - usedCount), allNumbers);

        return Result.success(unUsedInfo);
    }

    /**
     * sse测试
     * 
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}/used", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter send(@PathVariable Long id) {
        final String cacheKey = "sse_info_" + id;
        SseEmitter sseEmitter = SSE_EMITTER_MAP.computeIfAbsent(cacheKey, key -> new SseEmitter(0L));
        log.info(">>>>>当前sseEmitter:{}", sseEmitter);
        CompletableFuture.runAsync(() -> {
            log.info(">>>>>开始发送消息,当前时间为:{}", LocalDateTime.now());
            try {
                List<String> usedNumbers =
                    USED_NUMBER_MAP.getOrDefault(String.valueOf(id), new CopyOnWriteArrayList<>());
                USED_NUMBER_MAP.put(String.valueOf(id), usedNumbers);
                if (usedNumbers.size() == MESSAGE_INFOS.size()) {
                    return;
                }

                for (MessageInfo messageInfo : MESSAGE_INFOS) {
                    if (usedNumbers.contains(messageInfo.getNumber())) {
                        continue;
                    }
                    log.info(">>>>>开始发送消息，number:{}", messageInfo.getNumber());
                    this.sendData(sseEmitter, Result.success(messageInfo.getNumber()));
                    usedNumbers.add(messageInfo.getNumber());
                    // 人为休眠30秒
                    Thread.sleep(1000);
                }

            } catch (IOException | InterruptedException e) {
                log.error("Error", e);
            } finally {
                try {
                    this.sendData(sseEmitter, FINISHED);
                    this.closeSseEmitter(sseEmitter, cacheKey);
                } catch (IOException e) {
                    log.error("Error", e);
                }

            }

        }, taskExecutor);

        sseEmitter.onError(error -> {
            log.error(">>>>>onError", error);
            this.closeSseEmitter(sseEmitter, cacheKey);
        });
        sseEmitter.onCompletion(() -> {
            log.error(">>>>>onCompletion");
            this.closeSseEmitter(sseEmitter, cacheKey);
        });

        return sseEmitter;
    }

    private void closeSseEmitter(SseEmitter sseEmitter, String cacheKey) {
        sseEmitter.complete();
        SSE_EMITTER_MAP.remove(cacheKey);
    }

    private void sendData(@NonNull SseEmitter sseEmitter, Object data) throws IOException {
        sseEmitter.send(SseEmitter.event().reconnectTime(60000L).data(data));
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MessageInfo {

        private Long id;

        private String number;

        private LocalDateTime time;

        private Integer status;

        public static List<MessageInfo> createMessages() {
            int allCount = 20;
            return IntStream.rangeClosed(1, allCount).mapToObj(i -> {
                MessageInfo messageInfo =
                    new MessageInfo(Long.valueOf(i), System.currentTimeMillis() + "_" + i, LocalDateTime.now(), null);
                int status =
                    USED_NUMBER_MAP.getOrDefault(String.valueOf(i), Collections.emptyList()).size() < allCount ? 1 : 2;
                messageInfo.setStatus(status);

                return messageInfo;
            }).collect(Collectors.toList());

        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UnUsedInfo {
        private long id;

        private long allCount;

        private long usedCount;

        private long unUsedCount;

        private List<String> unUsedNumbers;
    }

    @Data
    public static class Result {

        private int code;

        private Object data;

        private Result(int code, Object data) {
            this.code = code;
            this.data = data;
        }

        public static Result success(Object data) {
            return new Result(0, data);
        }

        public static Result faiture(Object data) {
            return new Result(1, data);
        }
    }

}
