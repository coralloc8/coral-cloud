package com.coral.cloud.user.entity;

import com.coral.cloud.user.common.enums.Sex;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huss
 * @version 1.0
 * @className ChildDocument
 * @description child document
 * @date 2022/4/28 10:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChildDocument {

    /**
     * 人物id
     */
    private Long id;

    /**
     * 人物名称
     */
    private String personName;

    /**
     * 年龄
     */
    private Double age;

    /**
     * 性别
     */
    private Sex sex;

}
