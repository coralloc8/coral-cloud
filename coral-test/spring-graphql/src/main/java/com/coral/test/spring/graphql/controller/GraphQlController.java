package com.coral.test.spring.graphql.controller;

import com.coral.test.spring.graphql.model.Author;
import com.coral.test.spring.graphql.model.Book;
import com.coral.test.spring.graphql.service.BookService;
import jakarta.annotation.Resource;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

/**
 * graphql 控制器
 *
 * @author huss
 * @date 2023/12/28 14:21
 * @packageName com.coral.test.spring.graphql.controller
 * @className GraphQLController
 */
@Controller
public class GraphQlController {

    @Resource
    private BookService bookService;


    @QueryMapping
    public Optional<Book> findBook(@Argument("id") Long id) {
        return bookService.findBook(id);
    }

    @QueryMapping
    public Optional<Author> findAuthor(@Argument("authorNo") String authorNo) {
        return bookService.findAuthorByNo(authorNo);
    }

    @SchemaMapping(typeName = "Author", field = "books")
    public List<Book> findBooks(Author author) {
        return bookService.findBooks(author.getAuthorNo());
    }

    @SchemaMapping(typeName = "Book", field = "author")
    public Optional<Author> findAuthor(Book book) {
        return bookService.findAuthor(book);
    }

}
