package com.coral.test.spring.graphql_jdk8.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coral.base.common.StringUtils;
import com.coral.base.common.mybatis.service.impl.MybatisServiceImpl;
import com.coral.test.spring.graphql_jdk8.config.exception.BusinessException;
import com.coral.test.spring.graphql_jdk8.dto.BookPageQueryDTO;
import com.coral.test.spring.graphql_jdk8.entity.Author;
import com.coral.test.spring.graphql_jdk8.entity.Book;
import com.coral.test.spring.graphql_jdk8.mapper.BookMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 书籍服务
 *
 * @author huss
 * @date 2023/12/28 14:24
 * @packageName com.coral.test.spring.graphql.service
 * @className BookService
 */

@Service
@Slf4j
public class BookService extends MybatisServiceImpl<BookMapper, Book> {

    public Mono<Book> findBook(String bookName) {
        log.info(">>>>>[findBook] bookName:{}", bookName);
        if (LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond() % 2 == 0) {
            return Mono.error(new BusinessException("查询书籍信息失败"));
        }
        Wrapper<Book> wrapper = new QueryWrapper<Book>().lambda()
                .like(Book::getBookName, bookName);
        return Mono.just(this.getOne(wrapper));
    }

    public Mono<Book> findBook(Long id) {
        log.info(">>>>>[findBook] id:{}", id);
        if (LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond() % 2 == 0) {
            return Mono.error(new BusinessException("查询书籍信息失败"));
        }
        return Mono.just(this.getById(id));
    }

    public Mono<Map<Author, List<Book>>> findBooks(List<Author> authors) {
        Set<String> authorNos = authors.stream().map(Author::getAuthorNo).collect(Collectors.toSet());
        log.info(">>>>>[findBooks] authorNos:{}", authorNos);
        Wrapper<Book> wrapper = new QueryWrapper<Book>().lambda()
                .in(Book::getAuthorNo, authorNos);
        List<Book> books = this.list(wrapper);

        Map<String, Author> authorMap = authors.stream().collect(Collectors.toMap(Author::getAuthorNo, Function.identity(), (t1, t2) -> t2));

        Map<Author, List<Book>> bookMap = books.stream()
                .filter(e -> StringUtils.isNotBlank(e.getAuthorNo()))
                .collect(Collectors.groupingBy(k -> authorMap.get(k.getAuthorNo())));
        return Mono.just(bookMap);
    }

    public IPage<Book> findBooksByPage(BookPageQueryDTO bookPageQuery) {
        IPage<Book> page = Page.of(bookPageQuery.getPageQuery().getCurrent(), bookPageQuery.getPageQuery().getSize());
        Wrapper<Book> wrapper = new QueryWrapper<Book>().lambda()
                .like(StrUtil.isNotBlank(bookPageQuery.getBookName()), Book::getBookName, bookPageQuery.getBookName())
                .like(StrUtil.isNotBlank(bookPageQuery.getAuthorName()), Book::getAuthorName, bookPageQuery.getAuthorName())
                .ge(Objects.nonNull(bookPageQuery.getPriceStart()), Book::getPrice, bookPageQuery.getPriceStart())
                .le(Objects.nonNull(bookPageQuery.getPriceEnd()), Book::getPrice, bookPageQuery.getPriceEnd())
                .ge(Objects.nonNull(bookPageQuery.getChapterStart()), Book::getChapter, bookPageQuery.getChapterStart())
                .le(Objects.nonNull(bookPageQuery.getChapterEnd()), Book::getChapter, bookPageQuery.getChapterEnd());
        page = this.page(page, wrapper);
        return page;
    }


}
