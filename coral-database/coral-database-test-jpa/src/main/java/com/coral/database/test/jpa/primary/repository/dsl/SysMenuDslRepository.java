package com.coral.database.test.jpa.primary.repository.dsl;

/**
 * @description: 菜单
 * @author: huss
 * @time: 2020/7/13 14:28
 */
public interface SysMenuDslRepository {
    /**
     * @description 根据parentNo查询目前最大的no
     * @author huss
     * @email 452327322@qq.com
     * @date 2020/7/13 14:2
     * @param parentNo
     * @return a
     */
    String findLatestNoByParentNo(String parentNo);
}
