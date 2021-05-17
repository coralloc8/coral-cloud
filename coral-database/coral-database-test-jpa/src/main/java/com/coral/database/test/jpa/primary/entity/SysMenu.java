package com.coral.database.test.jpa.primary.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.coral.base.common.jpa.entity.IdAndStatusKey;

import lombok.Data;
import lombok.ToString;

/**
 * @description: 系统菜单
 * @author: huss
 * @time: 2020/7/7 16:16
 */
@Entity
@Table
@Data
@ToString(callSuper = true)
public class SysMenu extends IdAndStatusKey {

    /**
     * 菜单编号
     */
    private String no;

    /**
     * 父级编号
     */
    private String parentNo;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 唯一关键字
     */
    private String uniqueKey;

    /**
     * 路由
     */
    private String path;

    /**
     * 页面
     */
    private String page;

    /**
     * 组件
     */
    private String component;

    /**
     * 重定向路由
     */
    private String redirect;

    /**
     * 标题
     */
    private String title;

    /**
     * 图标
     */
    private String icon;

    /**
     * 总的权限值
     */
    private Integer right;




}
