package com.study.movida.service;

import com.study.movida.dto.ReplyDto;
import com.study.movida.entity.Board;
import com.study.movida.entity.Reply;

import java.util.List;

public interface ReplyService {

    Long register(ReplyDto replyDto);

    List<ReplyDto> getList(Long bno);

    void modify(ReplyDto replyDto);

    void remove(Long rno);

    default Reply dtoToEntity(ReplyDto replyDto) {
        Board board = Board.builder().bno(replyDto.getBno()).build();

        return Reply.builder()
                .rno(replyDto.getRno())
                .text(replyDto.getText())
                .replyer(replyDto.getReplyer())
                .board(board)
                .build();
    }

    default ReplyDto entityToDto(Reply reply) {
        return ReplyDto.builder()
                .rno(reply.getRno())
                .text(reply.getText())
                .replyer(reply.getReplyer())
                .regDate(reply.getRegDate())
                .modDate(reply.getModDate())
                .build();
    }



}
