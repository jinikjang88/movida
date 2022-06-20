package com.study.movida.service;

import com.study.movida.dto.BoardDto;
import com.study.movida.dto.PageRequestDto;
import com.study.movida.dto.PageResultDto;
import com.study.movida.entity.Board;
import com.study.movida.entity.Member;
import com.study.movida.repository.BoardRepository;
import com.study.movida.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;

    @Override
    public Long register(BoardDto dto) {
        log.info("dto :: {}", dto);
        Board board = dtoToEntity(dto);

        boardRepository.save(board);
        return board.getBno();
    }

    @Override
    public PageResultDto<BoardDto, Object[]> getList(PageRequestDto pageRequestDto) {
        log.info("pageRequestDto :: {}", pageRequestDto);

        Function<Object[], BoardDto> fn = (en -> entityToDto((Board) en[0], (Member) en[1], (Long)en[2]));
//        Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageRequestDto.getPageable(Sort.by("bno").descending()));

        Page<Object[]> result = boardRepository.searchPage(
                pageRequestDto.getType(),
                pageRequestDto.getKeyword(),
                pageRequestDto.getPageable(Sort.by("bno").descending())
        );

        return new PageResultDto<>(result, fn);
    }

    @Override
    public BoardDto get(Long bno) {

        Object result = boardRepository.getBoardByBno(bno);
        Object[] arr = (Object[]) result;
        return entityToDto((Board) arr[0], (Member) arr[1], (long)arr[2]);
    }

    @Transactional
    @Override
    public void removeWithReplies(Long bno) {
        replyRepository.deleteByBno(bno);
        boardRepository.deleteById(bno);
    }

    @Override
    public void modify(BoardDto boardDto) {
        Optional<Board> boardOptional = boardRepository.findById(boardDto.getBno());
        boardOptional.ifPresent(board -> {
            board.changeTitle(boardDto.getTitle());
            board.changeContent(boardDto.getContent());
            boardRepository.save(board);
        });
    }
}
