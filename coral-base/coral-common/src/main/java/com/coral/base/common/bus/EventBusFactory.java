package com.coral.base.common.bus;

/**
 * @author huss
 */
public final class EventBusFactory {

    public static IEventBus getEventBus() {
        EventBusType type = EventBusType.GUAVA;
        return type.getEventBus();
    }

    public static IEventBus getEventBus(EventBusType type) {
        return type.getEventBus();
    }

    public static enum EventBusType {
        GUAVA {
            @Override
            public IEventBus getEventBus() {
                return GEventBus.getInstance();
            }

        },

        ;

        public abstract IEventBus getEventBus();

    }

}
