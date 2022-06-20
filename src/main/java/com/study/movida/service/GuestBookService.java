package com.study.movida.service;

import com.study.movida.dto.GuestBookDto;
import com.study.movida.dto.PageRequestDto;
import com.study.movida.dto.PageResultDto;
import com.study.movida.entity.GuestBook;

public interface GuestBookService {

    Long register(GuestBookDto dto);

    GuestBookDto read(Long gno);

    PageResultDto<GuestBookDto, GuestBook> getList(PageRequestDto requestDto);

    void modify(GuestBookDto dto);

    void remove(Long gno);

    default GuestBook dtoToEntity(GuestBookDto dto) {
        return GuestBook.builder()
                .gno(dto.getGno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();
    }

    default GuestBookDto entityToDto(GuestBook guestBook) {
        return GuestBookDto.builder()
                .gno(guestBook.getGno())
                .title(guestBook.getTitle())
                .content(guestBook.getContent())
                .writer(guestBook.getWriter())
                .regDate(guestBook.getRegDate())
                .modDate(guestBook.getModDate())
                .build();
    }
}
