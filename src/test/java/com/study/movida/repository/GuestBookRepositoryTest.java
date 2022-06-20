package com.study.movida.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.study.movida.entity.GuestBook;
import com.study.movida.entity.QGuestBook;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootTest
class GuestBookRepositoryTest {

    @Autowired
    private GuestBookRepository guestBookRepository;

    @BeforeEach
    void insertDummies() {
        List<GuestBook> guestBookList = IntStream.rangeClosed(1, 300).mapToObj(i -> GuestBook.builder()
                .title("Title .. " + i)
                .content("Content .. " + i)
                .writer("user" + (i % 10))
                .build()).collect(Collectors.toList());

        guestBookRepository.saveAll(guestBookList);
    }

    @Test
    void updateGuestBook() {

        Optional<GuestBook> guestBookOptional = guestBookRepository.findById(300L);

        if (guestBookOptional.isPresent()) {
            GuestBook guestBook = guestBookOptional.get();
            guestBook.changeTitle("change title");
            guestBook.changeContent("change content");
            guestBookRepository.save(guestBook);

            Assertions.assertEquals(guestBook.getTitle(), "change title");
        }
    }

    @Test
    void testQuery() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());

        QGuestBook book = QGuestBook.guestBook;
        String keyword = "1";

        //where  문에 들어가는 조건들을 넣어주는 컨테이너
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        // 실지로 조건문
        BooleanExpression expression = book.title.contains(keyword);
        booleanBuilder.and(expression);

        Page<GuestBook> result = guestBookRepository.findAll(booleanBuilder, pageable);

        result.stream().forEach(System.out::println);
    }

    @Test
    void testQuery2() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());

        QGuestBook qguestBook = QGuestBook.guestBook;

        String keyword = "1";

        BooleanBuilder builder = new BooleanBuilder();

        BooleanExpression exTitle = qguestBook.title.contains(keyword);
        BooleanExpression exContent = qguestBook.content.contains(keyword);
        BooleanExpression exAll = exTitle.or(exContent);

        builder.and(exAll);

        builder.and(qguestBook.gno.gt(0L));

        Page<GuestBook> result = guestBookRepository.findAll(builder, pageable);

        result.stream().forEach(guestBook -> System.out.println("guestBook = " + guestBook));
    }
}
