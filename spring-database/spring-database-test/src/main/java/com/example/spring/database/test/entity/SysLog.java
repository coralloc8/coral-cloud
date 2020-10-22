package com.example.spring.database.test.entity;

import java.time.LocalDateTime;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.example.spring.common.jpa.entity.IdentityIdKey;
import com.example.spring.common.jpa.enums.GlobalYesOrNoEnum;

import lombok.Data;
import lombok.ToString;

/**
 * @description: 日志
 * @author: huss
 * @time: 2020/10/21 14:35
 */
@Entity
@Table
@Data
@ToString(callSuper = true)
public class SysLog extends IdentityIdKey {

    /**
     * 模块
     */
    private String module;

    /**
     * 入参
     */
    private String params;

    /**
     * 请求id
     */
    private String requestId;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 请求url
     */
    private String url;

    /**
     * ip地址
     */
    private String ip;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 状态
     */
    @Convert(converter = GlobalYesOrNoEnum.Convert.class)
    private GlobalYesOrNoEnum status;

    /**
     * 错误
     */
    private String error;
}
