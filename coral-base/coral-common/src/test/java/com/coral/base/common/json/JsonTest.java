package com.coral.base.common.json;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

/**
 * @author huss
 * @version 1.0
 * @className JsonTest
 * @description json测试
 * @date 2022/2/8 13:33
 */
public class JsonTest {

    @Test
    @DisplayName("toJson测试1")
    public void toJson1() {
        PushMessage pushMessage = PushMessage.builder()
                .pushKey(PushKey.WEIXIN)
                .sendTime(LocalDateTime.now())
                .build();

        System.out.println(JsonUtil.toJson(pushMessage));
    }
}
