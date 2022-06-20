package com.study.movida.service;

import com.study.movida.dto.ReplyDto;
import com.study.movida.entity.Board;
import com.study.movida.entity.Reply;
import com.study.movida.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;

    @Override
    public Long register(ReplyDto replyDto) {
        Reply reply = dtoToEntity(replyDto);
        replyRepository.save(reply);
        return reply.getRno();
    }

    @Override
    public List<ReplyDto> getList(Long bno) {
        List<Reply> repliesByBoardOrderByRno = replyRepository.getRepliesByBoardOrderByRno(Board.builder().bno(bno).build());

        return repliesByBoardOrderByRno.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    @Override
    public void modify(ReplyDto replyDto) {
        Reply reply = dtoToEntity(replyDto);
        replyRepository.save(reply);
    }

    @Override
    public void remove(Long rno) {
        replyRepository.deleteById(rno);
    }
}
