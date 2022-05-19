package com.coral.base.common.convert;

import lombok.Data;

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
public class ConvertModelVO {

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
    private Integer gender;

    /**
     * 开始日期
     */
    private LocalDate startDate;

    /**
     * 开始日期文本
     */
    private String startDateText;

    /**
     * 开始时间
     */
    private LocalTime startTime;

    /**
     * 开始日期+时间
     */
    private LocalDateTime startDateTime;

    /**
     * 状态
     */
    private StatusEnum status;

    /**
     * 状态文本
     */
    private String statusText;

    /**
     * 年龄
     */
    private String age;

    /**
     * 配置  json格式
     */
    private List<ConfigVO> config;

    /**
     * 第一个孩子
     */
    private ConvertSonModelVO myFirstSon;

    /**
     * 我的姓名+名字
     */
    private String myFullName;

    /**
     * 我的姓名+名字
     */
    private String myFullNameText;

    /**
     * 第一个孩子的名字
     */
    private String myFirstSonName;

    /**
     * 我的孩子列表
     */
    private List<ConvertSonModelVO> myChildren;


    /**
     * 第一个水果
     */
    private ConvertFruitModelVO myFirstFruit;

    /**
     * 水果集合
     */
    private List<ConvertFruitModelVO> myFruits;

    @Data
    public static class ConfigVO {

        private String appKey;

        private String url;
    }

    @Data
    public static class ConvertSonModelVO {

        /**
         * id
         */
        private Long id;

        /**
         * 名称
         */
        private String nameText;

    }
}
