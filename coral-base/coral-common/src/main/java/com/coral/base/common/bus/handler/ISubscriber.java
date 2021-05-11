package com.coral.base.common.bus.handler;

import com.coral.base.common.bus.event.AbstractEvent;

/**
 * 订阅者
 * 
 * @author huss
 */
public interface ISubscriber<T extends AbstractEvent> {

    void receive(T event);

}
