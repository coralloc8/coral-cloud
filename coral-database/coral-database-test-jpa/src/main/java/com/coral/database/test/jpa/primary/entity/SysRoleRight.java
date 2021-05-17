package com.coral.database.test.jpa.primary.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.coral.base.common.jpa.entity.IdentityIdKey;

import lombok.Data;
import lombok.ToString;

/**
 * @description: 角色权限
 * @author: huss
 * @time: 2020/7/7 16:33
 */
@Entity
@Table
@Data
@ToString(callSuper = true)
public class SysRoleRight extends IdentityIdKey {

    /**
     * 菜单编号
     */
    private String menuNo;

    /**
     * 角色编号
     */
    private Integer roleNo;

    /**
     * 权限值
     */
    private Integer right;

}
