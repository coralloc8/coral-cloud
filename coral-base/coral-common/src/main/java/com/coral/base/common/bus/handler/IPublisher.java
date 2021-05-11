package com.coral.base.common.bus.handler;

import com.coral.base.common.bus.event.AbstractEvent;

/**
 * 发布者
 * 
 * @author huss
 */
public interface IPublisher<T extends AbstractEvent> {
    void send(T event);
}
