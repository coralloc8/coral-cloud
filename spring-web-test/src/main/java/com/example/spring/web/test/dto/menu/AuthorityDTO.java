package com.example.spring.web.test.dto.menu;

import com.example.spring.common.convert.IConvert;
import com.example.spring.database.test.entity.SysMenuAuthority;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @description: 权限
 * @author: huss
 * @time: 2020/7/16 10:14
 */
@Getter
@Setter
@ToString
public class AuthorityDTO implements IConvert<SysMenuAuthority, AuthorityDTO>, Serializable {

    private String no;

    private String name;

    private String icon;

    private String uniqueKey;

    @Override
    public AuthorityDTO convert(SysMenuAuthority sysMenuAuthority) {
        this.no = sysMenuAuthority.getNo();
        this.name = sysMenuAuthority.getName();
        this.icon = sysMenuAuthority.getIcon();
        this.uniqueKey = sysMenuAuthority.getUniqueKey();
        return this;
    }
}
