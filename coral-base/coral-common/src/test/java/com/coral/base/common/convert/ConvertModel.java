package com.coral.base.common.convert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * @author huss
 * @version 1.0
 * @className ConvertModel
 * @description 转换model
 * @date 2022/5/18 16:29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConvertModel {

    /**
     * id
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 密码
     */
    private String password;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 开始日期
     */
    private LocalDate startDate;

    /**
     * 开始时间
     */
    private LocalTime startTime;

    /**
     * 开始日期+时间
     */
    private LocalDateTime startDateTime;

    /**
     * 状态枚举
     */
    private String status;

    /**
     * 年龄
     */
    private Double age;

    /**
     * 配置  json格式
     */
    private String config;


    /**
     * 第一个孩子
     */
    private ConvertSonModel firstSon;


    /**
     * 姓氏
     */
    private String firstName;

    /**
     * 名字
     */
    private String lastName;


    /**
     * 孩子列表
     */
    private List<ConvertSonModel> children;

    /**
     * 第一个水果
     */
    private ConvertFruitModel firstFruit;

    /**
     * 水果集合
     */
    private List<ConvertFruitModel> fruits;


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ConvertSonModel {

        /**
         * id
         */
        private Long id;

        /**
         * 名称
         */
        private String name;

    }
}
