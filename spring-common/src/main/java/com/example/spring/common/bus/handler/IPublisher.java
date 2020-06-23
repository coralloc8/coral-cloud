package com.example.spring.common.bus.handler;

import com.example.spring.common.bus.event.AbstractEvent;

/**
 * 发布者
 * 
 * @author huss
 */
public interface IPublisher<T extends AbstractEvent> {
    void send(T event);
}
