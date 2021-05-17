package com.coral.database.test.jpa.primary.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.coral.base.common.jpa.entity.IdAndStatusKey;

import lombok.Data;
import lombok.ToString;

/**
 * @description: 菜单权限
 * @author: huss
 * @time: 2020/7/7 16:22
 */
@Entity
@Table
@Data
@ToString(callSuper = true)
public class SysMenuAuthority extends IdAndStatusKey {

    /**
     * 编号
     */
    private String no;

    /**
     * 菜单编号
     */
    private String menuNo;
    /**
     * 权限名称
     */
    private String name;

    /**
     * 图标
     */
    private String icon;
    /**
     * 唯一键
     */
    private String uniqueKey;



}
