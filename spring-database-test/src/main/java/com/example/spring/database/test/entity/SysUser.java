package com.example.spring.database.test.entity;

import java.time.LocalDateTime;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.example.spring.common.jpa.entity.IdAndStatusKey;
import com.example.spring.common.jpa.enums.GlobalSexEnum;

import lombok.Data;
import lombok.ToString;

@Entity
@Table
@Data
@ToString(callSuper = true)
public class SysUser extends IdAndStatusKey {

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

    // 临时字段，暂时用来保存角色信息
    private String role;

}
