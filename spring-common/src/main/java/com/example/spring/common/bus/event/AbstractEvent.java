package com.example.spring.common.bus.event;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author huss
 */
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractEvent {

    /**
     * 事件类型
     */
    @Getter
    private DefaultEventIdentifier eventIdentifier;
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

    public AbstractEvent(DefaultEventIdentifier eventIdentifier, String eventId, LocalDateTime sendTime) {
        super();
        this.eventIdentifier = eventIdentifier;
        this.eventId = eventId;
        this.sendTime = sendTime;
    }

}
