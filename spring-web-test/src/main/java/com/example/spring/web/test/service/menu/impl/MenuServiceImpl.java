package com.example.spring.web.test.service.menu.impl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.spring.common.StringUtils;
import com.example.spring.common.jpa.enums.GlobalDeletedEnum;
import com.example.spring.common.jpa.service.impl.JpaBaseServiceImpl;
import com.example.spring.common.jpa.util.dsl.PredicateCreator;
import com.example.spring.database.test.entity.QSysMenu;
import com.example.spring.database.test.entity.SysMenu;
import com.example.spring.database.test.repository.SysMenuRepository;
import com.example.spring.web.test.dto.menu.MenuDTO;
import com.example.spring.web.test.service.menu.IMenuService;
import com.example.spring.web.test.vo.menu.request.MenuFilter;
import com.querydsl.core.types.Predicate;

import lombok.extern.slf4j.Slf4j;

/**
 * @description: 菜单服务
 * @author: huss
 * @time: 2020/7/14 15:54
 */
@Service
@Slf4j
public class MenuServiceImpl extends JpaBaseServiceImpl<SysMenu, Long, SysMenuRepository> implements IMenuService {

    /**
     * 查询导航栏菜单
     *
     * @param menuFilter
     * @return
     */
    @Override
    public List<MenuDTO> findNavigationBarMenus(MenuFilter menuFilter) {
        List<SysMenu> menuList = this.findAllOriginalMenus(menuFilter);
        // 数据转换
        return menuList.stream()
            //
            .map(new MenuDTO()::convert)
            //
            .collect(Collectors.toList());
    }

    /**
     * 查询所有原始数据菜单
     * 
     * @param menuFilter
     * @return
     */
    private List<SysMenu> findAllOriginalMenus(MenuFilter menuFilter) {
        Predicate predicate = this.buildPredicate(menuFilter);
        return (List<SysMenu>)this.getRepository().findAll(predicate);
    }

    /**
     * 条件组装
     * 
     * @param menuFilter
     * @return
     */
    private Predicate buildPredicate(MenuFilter menuFilter) {
        if (Objects.isNull(menuFilter)) {
            return null;
        }
        // 条件组装
        return PredicateCreator.builder()
            // 没有删除的
            .link(QSysMenu.sysMenu.deleted.eq(GlobalDeletedEnum.NO))
            //
            .link(StringUtils.isBlank(menuFilter.getKeyword()) ? null :
            //
                QSysMenu.sysMenu.no.eq(menuFilter.getKeyword())
                    //
                    .or(QSysMenu.sysMenu.title.eq(menuFilter.getKeyword())))
            //
            .build();
    }

}
