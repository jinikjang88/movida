package com.study.movida.service;

import com.study.movida.dto.BoardDto;
import com.study.movida.dto.PageRequestDto;
import com.study.movida.dto.PageResultDto;
import com.study.movida.entity.Board;
import com.study.movida.entity.Member;
import org.springframework.data.domain.PageRequest;

public interface BoardService {

    Long register(BoardDto dto);

    PageResultDto<BoardDto, Object[]> getList(PageRequestDto pageRequestDto);

    BoardDto get(Long bno);

    void removeWithReplies(Long bno);

    void modify(BoardDto boardDto);

    default Board dtoToEntity(BoardDto dto) {
        Member member = Member.builder()
                .email(dto.getWriterEmail()).build();

        return Board.builder()
                .bno(dto.getBno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(member)
                .build();
    }

    default BoardDto entityToDto(Board board, Member member, Long replyCount) {
        return BoardDto.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .writerEmail(member.getEmail())
                .writerName(member.getName())
                .replyCount(replyCount.intValue())
                .build();
    }
}
