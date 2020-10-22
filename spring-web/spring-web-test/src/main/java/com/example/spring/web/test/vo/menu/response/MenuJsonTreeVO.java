package com.example.spring.web.test.vo.menu.response;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.example.spring.common.CollectionUtil;
import com.example.spring.common.convert.IConvert;
import com.example.spring.common.convert.SimpleJsonTree;
import com.example.spring.web.test.dto.menu.MenuDTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @description: 菜单tree
 * @author: huss
 * @time: 2020/7/15 15:18
 */
@Getter
@Setter
@ToString
public class MenuJsonTreeVO extends SimpleJsonTree implements IConvert<MenuDTO, MenuJsonTreeVO> {

    private String no;
    private String parentNo;
    private String path;
    private String name;
    private String component;
    private String redirect;
    private String page;
    private Boolean hidden = false;
    private String title;
    private String icon;

    private List<AuthorityVO> authorities;

    /**
     * 是否有子级按钮级别的操作权限
     * 
     * @return Boolean
     */
    public Boolean getHasAuthority() {
        return CollectionUtil.isNotBlank(authorities);
    }

    @Override
    public MenuJsonTreeVO convert(MenuDTO e) {
        this.no = e.getNo();
        this.parentNo = e.getParentNo();
        this.name = e.getTitle();
        this.path = e.getPath();
        this.page = e.getPage();
        this.component = e.getComponent();
        this.redirect = e.getRedirect();
        this.title = e.getTitle();
        this.icon = e.getIcon();
        this.authorities = CollectionUtil.isNotBlank(e.getAuthorities())
            ? e.getAuthorities().parallelStream().map(new AuthorityVO()::convert).collect(Collectors.toList())
            : Collections.emptyList();
        return this;
    }
}
