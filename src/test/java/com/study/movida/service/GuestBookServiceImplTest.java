package com.study.movida.service;

import com.study.movida.dto.GuestBookDto;
import com.study.movida.dto.PageRequestDto;
import com.study.movida.dto.PageResultDto;
import com.study.movida.entity.GuestBook;
import com.study.movida.repository.GuestBookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootTest
class GuestBookServiceImplTest {

    @Autowired
    private GuestBookService service;

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
    void testRegister() {
        GuestBookDto dto = GuestBookDto.builder()
                .title("sample title")
                .content("sample content")
                .writer("user0")
                .build();

        System.out.println(service.register(dto));
    }

    @Test
    void testList() {
        PageRequestDto pageRequestDto = PageRequestDto.builder()
                .page(1).size(10).build();

        PageResultDto<GuestBookDto, GuestBook> list = service.getList(pageRequestDto);

        System.out.println("PREV : " + list.isPrev());
        System.out.println("NEXT : " + list.isPrev());
        System.out.println("TOTAL : " + list.getTotalPage());

        for (GuestBookDto guestBookDto : list.getDtoList()) {
            System.out.println(guestBookDto);
        }
    }

    @Test
    void testSearch() {
        PageRequestDto pageRequestDto = PageRequestDto.builder()
                .page(1)
                .size(10)
                .type("tc")
                .keyword("한글")
                .build();

        PageResultDto<GuestBookDto, GuestBook> resultDto = service.getList(pageRequestDto);

        System.out.println("PREV : " + resultDto.isPrev());
        System.out.println("NEXT : " + resultDto.isNext());
        System.out.println("TOTAL : " + resultDto.getTotalPage());

        System.out.println("--------------------------------");
        for (GuestBookDto dto : resultDto.getDtoList()) {
            System.out.println(dto);
        }

        System.out.println("--------------------------------");
        resultDto.getPageList().forEach(System.out::println);
    }
}
