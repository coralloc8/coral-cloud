package com.coral.database.test.jpa.entity;

import com.coral.base.common.jpa.entity.IdentityIdKey;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @description: 用户角色
 * @author: huss
 * @time: 2020/7/21 15:39
 */
@Entity
@Table
@Data
@ToString(callSuper = true)
public class SysUserRole extends IdentityIdKey {

    private Long userNo;

    private String roleNo;



}
