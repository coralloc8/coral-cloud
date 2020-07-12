package com.example.spring.database.test.entity;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.example.spring.common.jpa.entity.IdentityIdKey;
import com.example.spring.common.jpa.enums.GlobalDeletedEnum;
import com.example.spring.common.jpa.enums.GlobalYesOrNoEnum;

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
public class SysMenu extends IdentityIdKey {

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

    /**
     * 是否隐藏
     */
    @Convert(converter = GlobalYesOrNoEnum.Convert.class)
    private GlobalYesOrNoEnum hidden = GlobalYesOrNoEnum.NO;

    /**
     * 已删除
     */
    @Convert(converter = GlobalDeletedEnum.Convert.class)
    private GlobalDeletedEnum deleted = GlobalDeletedEnum.NO;


}
