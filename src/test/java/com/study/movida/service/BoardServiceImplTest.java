package com.study.movida.service;

import com.study.movida.dto.BoardDto;
import com.study.movida.dto.PageRequestDto;
import com.study.movida.dto.PageResultDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardServiceImplTest {

    @Autowired
    private BoardService boardService;

    @Test
    void testRegister() {
        BoardDto dto = BoardDto.builder()
                .title("Test ..")
                .content("Content test.. ")
                .writerEmail("user55@abc.com")
                .build();

        Long bno = boardService.register(dto);
    }

    @Test
    void testList() {
        PageRequestDto pageRequestDto = new PageRequestDto();

        PageResultDto<BoardDto, Object[]> result = boardService.getList(pageRequestDto);

        for (BoardDto boardDto : result.getDtoList()) {
            System.out.println(boardDto);
        }
    }

    @Test
    void testGet() {
        Long bno = 100L;
        BoardDto boardDto = boardService.get(bno);
        System.out.println(boardDto);
    }

    @Test
    public void restRemove() {
        Long bno = 1L;
        boardService.removeWithReplies(bno);
    }

    @Test
    void testModify() {
        BoardDto boardDto = BoardDto.builder()
                .bno(2L)
                .title("제목 변경")
                .content("내용 변경")
                .build();

        boardService.modify(boardDto);
    }


}
