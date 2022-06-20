package com.study.movida.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.study.movida.dto.GuestBookDto;
import com.study.movida.dto.PageRequestDto;
import com.study.movida.dto.PageResultDto;
import com.study.movida.entity.GuestBook;
import com.study.movida.entity.QGuestBook;
import com.study.movida.repository.GuestBookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

@Service
@Slf4j
@RequiredArgsConstructor
public class GuestBookServiceImpl implements GuestBookService {

    private final GuestBookRepository guestBookRepository;

    @Override
    public Long register(GuestBookDto dto) {
        GuestBook guestBook = dtoToEntity(dto);
        guestBookRepository.save(guestBook);
        return guestBook.getGno();
    }

    @Override
    public GuestBookDto read(Long gno) {
        Optional<GuestBook> guestBookOptional = guestBookRepository.findById(gno);

        return guestBookOptional.map(this::entityToDto).orElse(null);
    }

    @Override
    public PageResultDto<GuestBookDto, GuestBook> getList(PageRequestDto requestDto) {
        Pageable pageable = requestDto.getPageable(Sort.by("gno").descending());
        BooleanBuilder booleanBuilder = getSearch(requestDto);

        Page<GuestBook> result = guestBookRepository.findAll(booleanBuilder, pageable);
        Function<GuestBook, GuestBookDto> fn = (this::entityToDto);
        return new PageResultDto<>(result, fn);
    }

    @Override
    public void modify(GuestBookDto dto) {
        Optional<GuestBook> guestBookOptional = guestBookRepository.findById(dto.getGno());

        if(guestBookOptional.isPresent()) {
            GuestBook guestBook = guestBookOptional.get();
            guestBook.changeTitle(dto.getTitle());
            guestBook.changeContent(dto.getContent());
            guestBookRepository.save(guestBook);
        }
    }

    @Override
    public void remove(Long gno) {
        guestBookRepository.deleteById(gno);
    }

    private BooleanBuilder getSearch(PageRequestDto requestDto) {
        String type = requestDto.getType();

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QGuestBook qGuestBook = QGuestBook.guestBook;

        String keyword = requestDto.getKeyword();
        BooleanExpression expression = qGuestBook.gno.gt(0L);   // gno > 0

        booleanBuilder.and(expression);

        if (Objects.isNull(type) || type.trim().length() == 0 ) {
            // 검색 조건이 없는 경우
             return booleanBuilder;
        }

        //검색 조건을 작성하기
        BooleanBuilder conditionBuilder = new BooleanBuilder();
        if (type.contains("t")) {
            conditionBuilder.or(qGuestBook.title.contains(keyword));
        }
        if (type.contains("c")) {
            conditionBuilder.or(qGuestBook.content.contains(keyword));
        }
        if (type.contains("w")) {
            conditionBuilder.or(qGuestBook.writer.contains(keyword));
        }

        //모든 조건 통합
        booleanBuilder.and(conditionBuilder);

        return booleanBuilder;
    }
}
