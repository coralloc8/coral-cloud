package com.coral.test.spring.graphql_jdk8.mapper;

import com.coral.base.common.mybatis.mapper.MybatisMapper;
import com.coral.test.spring.graphql_jdk8.entity.Book;

/**
 * 书籍 repository
 *
 * @author huss
 * @date 2024/1/2 18:04
 * @packageName com.coral.test.spring.graphql.repository
 * @className BookRepository
 */
public interface BookMapper extends MybatisMapper<Book> {
}
