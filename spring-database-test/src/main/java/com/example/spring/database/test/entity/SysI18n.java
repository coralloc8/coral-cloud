package com.example.spring.database.test.entity;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.example.spring.common.jpa.entity.IdentityIdKey;
import com.example.spring.database.test.enums.I18nModuleEnum;

import lombok.Data;
import lombok.ToString;

/**
 * @description: 国际化配置
 * @author: huss
 * @time: 2020/7/13 10:34
 */
@Entity
@Table
@Data
@ToString(callSuper = true)
public class SysI18n extends IdentityIdKey {

    /**
     * 业务编号
     */
    private String infoNo;

    /**
     * 业务编号对应的类型
     */
    private String infoType;

    /**
     * 语言
     */
    private String lang;

    /**
     * 值
     */
    private String value;

    /**
     * 模块
     */
    @Convert(converter = I18nModuleEnum.Convert.class)
    private I18nModuleEnum module;

}
