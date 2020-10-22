package com.example.spring.web.test.vo.menu.response;

import java.io.Serializable;

import com.example.spring.common.convert.PojoConvert;
import com.example.spring.web.test.dto.menu.AuthorityDTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @description: 权限
 * @author: huss
 * @time: 2020/7/16 11:01
 */
@Getter
@Setter
@ToString
public class AuthorityVO implements PojoConvert<AuthorityDTO, AuthorityVO>, Serializable {

    private String no;

    private String name;

    private String icon;

    private String uniqueKey;

}
