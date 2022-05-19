package com.coral.base.common.convert;

import com.coral.base.common.json.JsonUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huss
 * @version 1.0
 * @className ConvertTest
 * @description 转换测试
 * @date 2022/5/18 16:38
 */
public class ConvertTest {

    private ConvertModel convertModel;


    @BeforeEach
    void init() {
        Map<String, Object> config = new HashMap<>();
        config.put("appKey", "remote");
        config.put("url", "http://192.168.29.31:7190/ops/remote");


        List<ConvertModel.ConvertSonModel> children = new ArrayList<>();
        ConvertModel.ConvertSonModel son = ConvertModel.ConvertSonModel.builder()
                .id(System.currentTimeMillis())
                .name("第一个孩子")
                .build();
        children.add(son);

        ConvertModel.ConvertSonModel son2 = ConvertModel.ConvertSonModel.builder()
                .id(System.currentTimeMillis())
                .name("第二个孩子")
                .build();
        children.add(son2);

        convertModel = ConvertModel.builder()
                .id(System.currentTimeMillis())
                .name("测试")
                .password("123456")
                .sex(1)
                .firstName("张")
                .lastName("三丰")
                .startDate(LocalDate.now())
                .startTime(LocalTime.now())
                .startDateTime(LocalDateTime.now())
                .status("normal")
                .age(20D)
                .config(JsonUtil.toJson(config))
                .firstSon(son)
                .children(children)
                .build();
    }

    @Test
    @DisplayName("转换测试1")
    public void convert1() {
        System.out.println(convertModel);
        ConvertModelVO vo = ConvertMapper.INSTANCE.convert(convertModel);
        System.out.println(vo);
    }


    @Test
    @DisplayName("转换测试2")
    public void convert2() {
        List<ConvertFruitModel> list = new ArrayList<>();

        ConvertFruitModel fruit = ConvertFruitModel.builder()
                .fruitName("香蕉")
                .money(18.999999D)
                .build();
        list.add(fruit);

        ConvertFruitModel fruit2 = ConvertFruitModel.builder()
                .fruitName("苹果")
                .money(5D)
                .build();
        list.add(fruit2);

        convertModel.setFirstFruit(fruit);
        convertModel.setFruits(list);

        System.out.println(convertModel);
        ConvertModelVO vo = ConvertMapper.INSTANCE.convert(convertModel);
        System.out.println(vo);
    }
}
