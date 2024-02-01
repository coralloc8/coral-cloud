package com.coral.test.spring.graphql_jdk8.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.coral.base.common.mybatis.service.impl.MybatisServiceImpl;
import com.coral.test.spring.graphql_jdk8.dto.AuthorQueryDTO;
import com.coral.test.spring.graphql_jdk8.entity.Author;
import com.coral.test.spring.graphql_jdk8.mapper.AuthorMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.Optional;

/**
 * 作者服务
 *
 * @author huss
 * @date 2024/1/2 18:06
 * @packageName com.coral.test.spring.graphql.service
 * @className AuthorService
 */
@Service
@Slf4j
public class AuthorService extends MybatisServiceImpl<AuthorMapper, Author> {

    public Optional<Author> findAuthorByNo(String authorNo) {
        log.info(">>>>>[findAuthorByNo] authorNo:{}", authorNo);
        Wrapper<Author> wrapper = new QueryWrapper<Author>().lambda()
                .eq(Author::getAuthorNo, authorNo);
        return Optional.of(this.getOne(wrapper));
    }

    public Flux<Author> findAuthors(AuthorQueryDTO authorQuery) {
        log.info(">>>>>[findAuthors] authorQuery:{}", authorQuery);
        Wrapper<Author> wrapper = new QueryWrapper<Author>().lambda()
                .in(CollUtil.isNotEmpty(authorQuery.getAuthorNos()), Author::getAuthorNo, authorQuery.getAuthorNos())
                .eq(Objects.nonNull(authorQuery.getSex()), Author::getSex, authorQuery.getSex())
                .ge(Objects.nonNull(authorQuery.getBirthdayStart()), Author::getBirthday, authorQuery.getBirthdayStart())
                .le(Objects.nonNull(authorQuery.getBirthdayEnd()), Author::getBirthday, authorQuery.getBirthdayEnd())
                .like(StrUtil.isNotBlank(authorQuery.getAuthorName()), Author::getAuthorName, authorQuery.getAuthorName());
        return Flux.fromIterable(this.list(wrapper));
    }
}
