package com.coral.base.common.bus.handler;

import com.coral.base.common.bus.event.AbstractEvent;
import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;

import lombok.extern.slf4j.Slf4j;

/**
 * @author huss
 */
@Slf4j
public abstract class AbstractSubscriber<T extends AbstractEvent> implements ISubscriber<T> {

    @Subscribe
    @AllowConcurrentEvents
    @Override
    public void receive(T event) {
        log.info(">>>>>收到事件,事件标识:{},事件ID:%s,内容:{}\n", event.getEventIdentifier(), event.getEventId(), event);
        receiveHandle(event);
        log.info(">>>>>事件处理结束,事件标识:{},事件ID:{}\n", event.getEventIdentifier(), event.getEventId());
    }

    /**
     * 处理订阅事件
     *
     * @param event
     */
    protected abstract void receiveHandle(T event);

}
