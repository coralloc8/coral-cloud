package com.coral.database.test.jpa.primary.repository.dsl;

import com.coral.database.test.jpa.primary.entity.QSysMenu;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.coral.base.common.StringUtils;
import com.coral.database.test.jpa.primary.entity.SysMenu;
import com.querydsl.core.types.Predicate;

/**
 * @description: 菜单
 * @author: huss
 * @time: 2020/7/13 14:29
 */
@Repository
public class SysMenuRepositoryImpl extends QuerydslRepositorySupport implements SysMenuDslRepository {

    /**
     * Creates a new {@link QuerydslRepositorySupport} instance for the given domain type.*
     */
    public SysMenuRepositoryImpl() {
        super(SysMenu.class);
    }

    @Override
    public String findLatestNoByParentNo(String parentNo) {

        Predicate predicate;
        if (StringUtils.isBlank(parentNo)) {
            predicate = QSysMenu.sysMenu.parentNo.isNull().or(QSysMenu.sysMenu.parentNo.isEmpty());
        } else {
            predicate = QSysMenu.sysMenu.parentNo.eq(parentNo);
        }
        return from(QSysMenu.sysMenu).select(QSysMenu.sysMenu.no.max()).where(predicate).fetchOne();
    }
}
