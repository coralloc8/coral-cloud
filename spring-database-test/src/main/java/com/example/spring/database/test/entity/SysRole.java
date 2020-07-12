package com.example.spring.database.test.entity;

import com.example.spring.common.jpa.entity.IdentityIdKey;
import com.example.spring.common.jpa.enums.GlobalDeletedEnum;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @description: 角色
 * @author: huss
 * @time: 2020/7/7 16:33
 */
@Entity
@Table
@Data
@ToString(callSuper = true)
public class SysRole extends IdentityIdKey {

    /**
     * 编号
     */
    private Integer no;

    /**
     * 名称
     */
    private String name;

    /**
     * 唯一键
     */
    private String uniqueKey;
    /**
     * 已删除
     */
    @Convert(converter = GlobalDeletedEnum.Convert.class)
    private GlobalDeletedEnum deleted = GlobalDeletedEnum.NO;
}
