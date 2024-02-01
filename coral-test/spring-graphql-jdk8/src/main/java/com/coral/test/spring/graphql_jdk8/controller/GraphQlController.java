package com.coral.test.spring.graphql_jdk8.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.coral.base.common.json.JsonUtil;
import com.coral.test.spring.graphql_jdk8.dto.AuthorQueryDTO;
import com.coral.test.spring.graphql_jdk8.dto.BookPageQueryDTO;
import com.coral.test.spring.graphql_jdk8.entity.Author;
import com.coral.test.spring.graphql_jdk8.entity.Book;
import com.coral.test.spring.graphql_jdk8.service.AuthorService;
import com.coral.test.spring.graphql_jdk8.service.BookService;
import com.coral.test.spring.graphql_jdk8.service.UserService;
import com.coral.test.spring.graphql_jdk8.util.ConnectionAssemblerUtil;
import com.coral.test.spring.graphql_jdk8.vo.PageVO;
import com.coral.test.spring.graphql_jdk8.vo.UserInfoVO;
import graphql.relay.Connection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.*;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * graphql 控制器
 *
 * @author huss
 * @date 2023/12/28 14:21
 * @packageName com.coral.test.spring.graphql.controller
 * @className GraphQLController
 */
@Slf4j
@Controller
public class GraphQlController {

    @Resource
    private BookService bookService;

    @Resource
    private AuthorService authorService;

    @Resource
    private UserService userService;


    @QueryMapping
    public Mono<Book> book(@Argument("id") Long id) {
        return bookService.findBook(id);
    }

    @QueryMapping
    public Optional<Author> author(@Argument("authorNo") String authorNo) {
        return authorService.findAuthorByNo(authorNo);
    }

    @QueryMapping
    public Flux<Author> authors(@Argument("query") @Valid AuthorQueryDTO authorQuery) {
        return authorService.findAuthors(authorQuery);
    }

    @QueryMapping
    public Mono<Connection<Book>> booksOfPage(@Argument("query") @Valid BookPageQueryDTO bookPageQuery) {
//        return bookService.findAuthors(authorQuery);
        IPage<Book> page = bookService.findBooksByPage(bookPageQuery);
        return Mono.just(ConnectionAssemblerUtil.convert(page));
    }

    @QueryMapping
    public Mono<PageVO<Book>> booksOfPage2(@Argument("query") @Valid BookPageQueryDTO bookPageQuery) {
        IPage<Book> page = bookService.findBooksByPage(bookPageQuery);
        log.info(">>>>>page:{}", JsonUtil.toJson(page));
        return Mono.just(PageVO.of(page));
    }

    @QueryMapping
    public Mono<PageVO<Book>> booksOfPage3(@Argument("query") @Valid BookPageQueryDTO bookPageQuery) {
        IPage<Book> page = bookService.findBooksByPage(bookPageQuery);
        log.info(">>>>>page:{}", JsonUtil.toJson(page));
        return Mono.just(PageVO.of(page));
    }

    @SchemaMapping(typeName = "Book", field = "author")
    public Optional<Author> findAuthor(Book book) {
        return authorService.findAuthorByNo(book.getAuthorNo());
    }

    @SchemaMapping(typeName = "Book", field = "user")
    public Optional<UserInfoVO> findAuthor(Book book, @ContextValue(required = false) String account) {
        return userService.findUserInfo(account);
    }

    @BatchMapping(typeName = "Author", field = "books")
    public Mono<Map<Author, List<Book>>> findBooks(List<Author> authors) {
        return bookService.findBooks(authors);
    }

}
