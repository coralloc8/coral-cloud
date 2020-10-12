package com.example.spring.common.bus;

import com.example.spring.common.bus.event.AbstractEvent;
import com.example.spring.common.bus.event.DefaultEventIdentifier;
import com.example.spring.common.bus.event.IEventIdentifier;
import com.example.spring.common.bus.handler.ISubscriber;

/**
 * @author huss
 */
public interface IEventBus {

    /**
     * 
     * @description 注册
     * @author huss
     * @email 452327322@qq.com
     * @date 2019年7月10日下午8:11:16
     * @param subscribe
     *            订阅
     * @param identifier
     *            标识
     * @return IEventBus
     */
    @SuppressWarnings("rawtypes")
    public IEventBus register(ISubscriber subscribe, IEventIdentifier identifier);

    /**
     * 取消注册
     * 
     * @param subscribe
     * @param identifier
     * @return
     */
    @SuppressWarnings("rawtypes")
    public IEventBus unregister(ISubscriber subscribe, IEventIdentifier identifier);

    /**
     * 发送事件
     * 
     * @param event
     * @return
     */
    public boolean post(AbstractEvent event);

    /**
     * 获取事件标识
     * 
     * @param identifier
     * @return
     */
    public String eventIdentifier(IEventIdentifier identifier);
}
