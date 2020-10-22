package com.example.spring.common.bus.handler;

import com.example.spring.common.bus.event.AbstractEvent;

/**
 * 订阅者
 * 
 * @author huss
 */
public interface ISubscriber<T extends AbstractEvent> {

    void receive(T event);

}
