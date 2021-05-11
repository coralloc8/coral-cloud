package com.coral.base.common.bus.handler;

import com.coral.base.common.bus.event.AbstractEvent;
import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import lombok.extern.slf4j.Slf4j;

/**
 * 发布者和订阅者
 * 
 * @author huss
 */
@Slf4j
public abstract class AbstractPublisherSubscriber<SubscriberEvent extends AbstractEvent,
    PublisherEvent extends AbstractEvent> extends AbstractPublisher<PublisherEvent>
    implements ISubscriber<SubscriberEvent> {

    @Subscribe
    @AllowConcurrentEvents
    @Override
    public void receive(SubscriberEvent event) {
        log.info(">>>>>收到事件,事件标识:{},事件ID:{},内容:{}", event.getEventIdentifier(), event.getEventId(), event);
        receiveHandle(event);
        log.info(">>>>>事件处理结束,事件标识:{},事件ID:{}", event.getEventIdentifier(), event.getEventId());
    }

    /**
     * 处理订阅事件
     * 
     * @param event
     */
    protected abstract void receiveHandle(SubscriberEvent event);

}
