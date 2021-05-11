package com.coral.base.common.bus.handler;

import com.coral.base.common.bus.EventBusFactory;
import com.coral.base.common.bus.event.AbstractEvent;

import lombok.extern.slf4j.Slf4j;

/**
 * @author huss
 */
@Slf4j
public abstract class AbstractPublisher<T extends AbstractEvent> implements IPublisher<T> {

    @Override
    public void send(T event) {
        log.info("#####开始发布事件,事件标识:{},事件ID:{},发送时间:{}\n", event.getEventIdentifier(), event.getEventId(),
            event.getSendTime());
        sendHandle(event);
        log.info("#####事件发布结束,事件标识:{},事件ID:{}\n", event.getEventIdentifier(), event.getEventId());
    }

    /**
     * 处理发送事件
     *
     * @param event
     */
    protected void sendHandle(T event) {
        EventBusFactory.getEventBus().post(event);
    }
}
