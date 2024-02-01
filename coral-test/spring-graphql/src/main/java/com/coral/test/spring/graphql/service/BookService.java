package com.coral.test.spring.graphql.service;

import com.coral.test.spring.graphql.eums.Sex;
import com.coral.test.spring.graphql.model.Author;
import com.coral.test.spring.graphql.model.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
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
public class BookService {

    private final static List<Book> BOOK_LIST = new CopyOnWriteArrayList<>();
    private final static List<Author> AUTHOR_LIST = new CopyOnWriteArrayList<>();


    public Optional<Book> findBook(Long id) {
        log.info(">>>>>[findBook] id:{}", id);
        return BOOK_LIST.stream().filter(book -> book.getId().equals(id)).findAny();
    }

    public Optional<Author> findAuthorByNo(String authorNo) {
        log.info(">>>>>[findAuthorByNo] authorNo:{}", authorNo);
        return AUTHOR_LIST.stream().filter(author -> author.getAuthorNo().equals(authorNo)).findAny();
    }

    public List<Book> findBooks(String authorNo) {
        log.info(">>>>>[findBooks] authorNo:{}", authorNo);
        return BOOK_LIST.stream().filter(book -> book.getAuthorNo().equals(authorNo)).collect(Collectors.toList());
    }

    public Optional<Author> findAuthor(Book book) {
        log.info(">>>>>[findAuthor] authorNo:{}", book.getAuthorNo());
        return AUTHOR_LIST.stream().filter(author -> author.getAuthorNo().equals(book.getAuthorNo())).findAny();
    }

    static {
        BOOK_LIST.add(Book.builder()
                .id(1L).bookName("校花的贴身高手")
                .authorNo("1001").authorName("鱼人二代")
                .price(50D).chapter(15000L)
                .createTime(LocalDateTime.of(2011, 1, 1, 0, 0, 0))
                .build()
        );
        //
        BOOK_LIST.add(Book.builder()
                .id(2L).bookName("神墓")
                .authorNo("1002").authorName("辰东")
                .price(100D).chapter(1200L)
                .createTime(LocalDateTime.of(2008, 6, 2, 0, 0, 0))
                .build()
        );
        //
        BOOK_LIST.add(Book.builder()
                .id(3L).bookName("遮天")
                .authorNo("1002").authorName("辰东")
                .price(50D).chapter(3000L)
                .createTime(LocalDateTime.of(2014, 4, 23, 0, 0, 0))
                .build()
        );
        //
        BOOK_LIST.add(Book.builder()
                .id(4L).bookName("完美世界")
                .authorNo("1002").authorName("辰东")
                .price(50D).chapter(2500L)
                .createTime(LocalDateTime.of(2018, 9, 12, 0, 0, 0))
                .build()
        );
        //
        BOOK_LIST.add(Book.builder()
                .id(5L).bookName("一念永恒")
                .authorNo("1003").authorName("耳根")
                .price(50D).chapter(1000L)
                .createTime(LocalDateTime.of(2015, 5, 3, 0, 0, 0))
                .build()
        );

        // 作者
        AUTHOR_LIST.add(Author.builder()
                .id(1L)
                .authorNo("1001").authorName("鱼人二代")
                .sex(Sex.FEMALE).birthday(LocalDate.of(1984, 1, 23))
                .createTime(LocalDateTime.now())
                .build()
        );
        AUTHOR_LIST.add(Author.builder()
                .id(2L)
                .authorNo("1002").authorName("辰东")
                .sex(Sex.MALE).birthday(LocalDate.of(1987, 12, 15))
                .createTime(LocalDateTime.now())
                .build()
        );
        AUTHOR_LIST.add(Author.builder()
                .id(3L)
                .authorNo("1003").authorName("耳根")
                .sex(Sex.MALE).birthday(LocalDate.of(1980, 8, 3))
                .createTime(LocalDateTime.now())
                .build()
        );


    }
}
