package com.coral.base.common.json;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Test
    @DisplayName("parseArray测试")
    public void parseArray() {

        List<PushMessage> list = new ArrayList<>();
        PushMessage pushMessage = PushMessage.builder()
                .pushKey(PushKey.WEIXIN)
                .sendTime(LocalDateTime.now())
                .build();
        list.add(pushMessage);

        pushMessage = PushMessage.builder()
                .pushKey(PushKey.ZHIFUBAO)
                .sendTime(LocalDateTime.now())
                .build();
        list.add(pushMessage);

        String json = JsonUtil.toJson(list);
        System.out.println(json);

        list = JsonUtil.parseArray(json, PushMessage.class);
        System.out.println(list);
        System.out.println(list.get(0).getPushKey());
    }


    @Test
    @DisplayName("parseArray2测试")
    public void parseArray2() {
        List<String> list = new ArrayList<>();
        list.add("123");
        list.add("234");

        String json = JsonUtil.toJson(list);
        System.out.println(json);

        list = JsonUtil.parseArray(json, String.class);
        System.out.println(list);
        System.out.println(list.get(0).getClass());
    }

    @Test
    @DisplayName("parseArray3测试")
    public void parseArray3() {
        List<Integer> list = new ArrayList<>();
        list.add(123);
        list.add(234);

        String json = JsonUtil.toJson(list);
        System.out.println(json);

        list = JsonUtil.parseArray(json, Integer.class);
        System.out.println(list);
        System.out.println(list.get(0).getClass());
    }

}
