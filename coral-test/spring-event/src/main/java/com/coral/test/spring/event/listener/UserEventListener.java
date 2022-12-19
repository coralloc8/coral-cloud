package com.coral.test.spring.event.listener;

import com.coral.base.common.json.JsonUtil;
import com.coral.test.spring.event.domain.UserChanged;
import com.coral.test.spring.event.domain.UserCreated;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author huss
 * @version 1.0
 * @className BaseEventListener
 * @description 事件监听
 * @date 2022/12/10 14:05
 */
@Slf4j
@Component
public class UserEventListener {


    @EventListener
    public void eventListener(BaseEvent event) {
        log.info("##### [eventListener] 收到事件,开始执行.事件ID:{},入参:{}", event.getEventId(), JsonUtil.toJson(event));
        if (event.getEvent() instanceof UserCreated) {
            userCreated((UserCreated) event.getEvent());
        }
        if (event.getEvent() instanceof UserChanged) {
            userChanged((UserChanged) event.getEvent());
        }

        log.info("##### [eventListener] 事件执行结束. 事件ID:{}", event.getEvent());
    }

    private void userChanged(UserChanged userChanged) {
        log.info(">>>>> userChanged 111111.");
        try {
            Thread.sleep(6000);
            System.out.println("userChanged 完成 111111.");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void userCreated(UserCreated userCreated) {
        log.info(">>>>> userCreated.");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
