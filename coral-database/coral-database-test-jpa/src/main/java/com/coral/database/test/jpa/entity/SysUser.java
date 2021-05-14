package com.coral.database.test.jpa.entity;

import java.time.LocalDateTime;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.coral.base.common.jpa.entity.IdAndStatusKey;

import com.coral.base.common.jpa.enums.GlobalSexEnum;
import lombok.Data;
import lombok.ToString;

/**
 * @author huss
 */
@Entity
@Table
@Data
@ToString(callSuper = true)
public class SysUser extends IdAndStatusKey {

    /**
     * 用户编号
     */
    private Long no;

    private String account;

    private String password;

    private String salt;

    private String username;

    private String phone;

    private Integer workNo;

    private String headerImage;

    @Convert(converter = GlobalSexEnum.Convert.class)
    private GlobalSexEnum sex;

    private Boolean isAdmin;

    private LocalDateTime createTime;

    private Long createUser;

    private LocalDateTime updateTime;

    private Long updateUser;

    private String email;

}
