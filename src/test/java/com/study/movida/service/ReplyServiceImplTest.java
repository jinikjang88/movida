package com.study.movida.service;

import com.study.movida.dto.ReplyDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReplyServiceImplTest {

    @Autowired
    private ReplyService replyService;

    @Test
    void testGetList() {
        Long bno = 100L;

        List<ReplyDto> replyDtoList = replyService.getList(bno);

        replyDtoList.forEach(System.out::println);
    }




}
