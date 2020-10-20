package com.example.spring.common.bus.event;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Getter;
import lombok.ToString;

/**
 * @author huss
 */
@ToString
public abstract class AbstractEvent {

    /**
     * 事件id
     */
    @Getter
    private String eventId;
    /**
     * 发送时间
     */
    @Getter
    private LocalDateTime sendTime;

    /**
     * 获取事件标识
     *
     * @return
     */
    public IEventIdentifier getEventIdentifier() {
        return DefaultEventIdentifier.DEFAULT;
    }

    protected AbstractEvent() {
        this(UUID.randomUUID().toString());
    }

    public AbstractEvent(String eventId) {
        super();
        this.eventId = eventId;
        this.sendTime = LocalDateTime.now();
    }

}
