package com.example.spring.common.bus;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;

import com.example.spring.common.bus.event.AbstractEvent;
import com.example.spring.common.bus.event.IEventIdentifier;
import com.example.spring.common.bus.handler.ISubscriber;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class GEventBus implements IEventBus {

    private final static Map<String, EventBus> BUS_MAP = new ConcurrentHashMap<>();

    private final static GEventBus _THIS = new GEventBus();

    private GEventBus() {
        super();
    }

    public static IEventBus getInstance() {
        return _THIS;
    }

    /**
     *
     * @description 获取bus，必须在bus初始化之后
     * @author huss
     * @email 452327322@qq.com
     * @date 2019年7月10日下午7:10:44
     * @param identifier
     * @return IEventBus
     */
    private EventBus getBus(@NonNull IEventIdentifier identifier) {
        EventBus bus = BUS_MAP.get(identifier.getIdentifierKey());
        if (bus == null) {
            throw new NullPointerException("GEventBus not init...");
        }
        return bus;
    }

    /**
     * 
     * @description bus初始化
     * @author huss
     * @email 452327322@qq.com
     * @date 2019年7月10日下午7:04:27
     * @param identifier
     *            事件类型
     * 
     * @return IEventBus
     */
    private EventBus init(@NonNull IEventIdentifier identifier) {
        ThreadPoolExecutor executorService = identifier.needThreadPool()
            ? (ThreadPoolExecutor)identifier.getThreadPool() : (ThreadPoolExecutor)identifier.getDefaultThreadPool();

        log.info(">>>>>当前使用线程池名称:" + executorService.getThreadFactory().toString() + ","
            + executorService.getCorePoolSize() + "," + executorService.getMaximumPoolSize());
        EventBus bus = BUS_MAP.computeIfAbsent(identifier.getIdentifierKey(),
            k -> new AsyncEventBus(identifier.getIdentifierKey(), executorService));
        return bus;
    }

    @Override
    public IEventBus register(@SuppressWarnings("rawtypes") @NonNull ISubscriber subscribe,
        @NonNull IEventIdentifier identifier) {
        EventBus bus = init(identifier);
        bus.register(subscribe);
        return this;
    }

    @Override
    public boolean post(@NonNull AbstractEvent event) {
        EventBus bus = getBus(event.getEventIdentifier());
        try {
            bus.post(event);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public String eventIdentifier(@NonNull IEventIdentifier identifier) {
        EventBus bus = getBus(identifier);
        return bus.identifier();
    }

    @Override
    public IEventBus unregister(@SuppressWarnings("rawtypes") @NonNull ISubscriber subscribe,
        @NonNull IEventIdentifier identifier) {
        EventBus bus = getBus(identifier);
        bus.unregister(subscribe);
        return this;
    }

}
