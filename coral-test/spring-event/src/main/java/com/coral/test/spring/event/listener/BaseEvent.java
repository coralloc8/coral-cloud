package com.coral.test.spring.event.listener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.core.ResolvableType;
import org.springframework.core.ResolvableTypeProvider;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author huss
 * @version 1.0
 * @className BaseEvent
 * @description 基础事件
 * @date 2022/12/10 13:57
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class BaseEvent<T> implements ResolvableTypeProvider {

    @Getter
    private T event;

    @Getter
    private LocalDateTime sendTime;

    @Getter
    private String eventId;

    /**
     * 创建事件
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> BaseEvent<T> createEvent(T data) {
        return new BaseEvent<>(data, LocalDateTime.now(), UUID.randomUUID().toString());
    }


    @JsonIgnore
    @Override
    public ResolvableType getResolvableType() {
        return ResolvableType.forClassWithGenerics(getClass(), ResolvableType.forClass(getEvent().getClass()));
    }
}
