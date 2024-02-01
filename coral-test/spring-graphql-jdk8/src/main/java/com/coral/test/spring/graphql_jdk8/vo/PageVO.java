package com.coral.test.spring.graphql_jdk8.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页数据
 *
 * @author huss
 * @date 2024/1/3 17:47
 * @packageName com.coral.test.spring.graphql.vo
 * @className PageVO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageVO<T> {

    /**
     * 生成分页数据
     *
     * @param page
     * @param <T>
     * @return
     */
    public static <T> PageVO<T> of(IPage<T> page) {
        return new PageVO<>(page.getRecords(), new PageInfoVO(
                page.getTotal(), page.getSize(), page.getCurrent(), page.getPages(),
                page.getCurrent() > 1, page.getCurrent() < page.getPages()
        ));
    }

    // #############################


    private List<T> records;

    private PageInfoVO pageInfo;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class PageInfoVO {
        /**
         * 总数据量
         */
        private long total;
        /**
         * 每页显示数据量
         */
        private long size;
        /**
         * 当前页数
         */
        private long current;
        /**
         * 总页数
         */
        private long pages;
        /**
         * 是否有上一页
         */
        private boolean hasPreviousPage;
        /**
         * 是否有下一页
         */
        private boolean hasNextPage;

    }
}
