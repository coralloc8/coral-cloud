package com.coral.web.test.dto.menu;

import java.util.List;

import com.coral.database.test.jpa.entity.SysMenu;

import lombok.*;

/**
 * @description: 菜单
 * @author: huss
 * @time: 2020/7/15 14:41
 */
@Getter
@ToString
@NoArgsConstructor
public class MenuDTO extends SimpleMenuDTO {

    /**
     * 设置资源权限
     * 
     * @param authorities
     */
    public MenuDTO(List<AuthorityDTO> authorities) {
        this.setAuthorities(authorities);
    }

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
     * 权限集合
     */
    @Setter(AccessLevel.PRIVATE)
    private List<AuthorityDTO> authorities;

    /**
     * 数据转换
     *
     * @param e
     *            菜单数据
     * @return SimpleMenuDTO
     */
    @Override
    public MenuDTO convert(@NonNull SysMenu e) {
        super.convert(e);
        this.path = e.getPath();
        this.page = e.getPage();
        this.component = e.getComponent();
        this.redirect = e.getRedirect();
        this.title = e.getTitle();
        this.icon = e.getIcon();
        this.right = e.getRight();
        return this;
    }
}
