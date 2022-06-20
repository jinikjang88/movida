package com.study.movida.repository;

import com.study.movida.entity.Memo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MemoRepositoryTest {

    @Autowired
    MemoRepository memoRepository;

    @BeforeEach
    void init() {
        memoRepository.deleteAll();
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Memo memo = Memo.builder().memoText("sample .. " + i).build();
            memoRepository.save(memo);
        });
    }

    @Test
    void getRepositoryClassName() {
        // 프록시 이름이 출력
        System.out.println(memoRepository.getClass().getName());
    }

    @Test
    void testSaveMemo() {
        //given
        //when

        //then
        List<Memo> all = memoRepository.findAll();
        assertThat(all.size()).isEqualTo(100);
    }

    @Test
    void testFindById() {
        //given
        //when
        Long memoId = 100L;
        Optional<Memo> memoOptional = memoRepository.findById(memoId);

        //then
        memoOptional.ifPresent(memo -> assertThat(memo.getMemoText()).isEqualTo("sample .. " + memoId));
    }

    @Test
    void testGetOne() {
        //given
        //when
        Long memoId = 100L;
        Memo memo = memoRepository.getOne(memoId);

        assertThat(memoId).isEqualTo(memo.getId());
    }

    @Test
    void testUpdate() {
        //given
        //when
        Memo memo = Memo.builder().id(100L).memoText("Update Text").build();
        memoRepository.save(memo);

        //then
        memoRepository.findById(100L)
                .ifPresent(memo1 -> assertThat(memo1.getMemoText())
                        .isEqualTo(memo.getMemoText()));
    }

    @Test
    void testDelete() {
//        //given
//        //when
//        Long memoId = 100L;
//        memoRepository.deleteById(memoId);
//
//        List<Memo> all = memoRepository.findAll();
//
//        //then
//        assertThat(all.size()).isEqualTo(99);
    }

    @Test
    void testPageDefault() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Memo> result = memoRepository.findAll(pageable);

        List<Memo> content = result.getContent();

        assertThat(content.size()).isEqualTo(10);
        assertThat(result.getNumberOfElements()).isEqualTo(10);
        assertThat(result.getTotalElements()).isEqualTo(100);
        assertThat(result.getTotalPages()).isEqualTo(10);
        assertThat(result.getNumber()).isEqualTo(0);
        assertThat(result.getSize()).isEqualTo(10);
        assertThat(result.hasNext()).isEqualTo(true);
    }

    @Test
    void testPageSort() {
        Sort sort = Sort.by("id").descending();
        Pageable pageable = PageRequest.of(0, 10, sort);

        Page<Memo> result = memoRepository.findAll(pageable);

        for (Memo memo : result) {
            System.out.println("memo = " + memo);
        }
    }

    @Test
    void testPageSort2() {
        Sort sort = Sort.by("id").descending();
        Sort sort1 = Sort.by("memoText").ascending();
        Sort sortAll = sort.and(sort1);

        Pageable pageable = PageRequest.of(0, 10, sortAll);

        Page<Memo> result = memoRepository.findAll(pageable);

        for (Memo memo : result) {
            System.out.println("memo = " + memo);
        }
    }

    @Test
    void testQueryMethodList() {
        List<Memo> memoList = memoRepository.findByIdBetweenOrderByIdDesc(70L, 80L);

        assertThat(memoList.size()).isEqualTo(0);
    }

    @Test
    void testQueryMethodPageable() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());
        Page<Memo> result = memoRepository.findByIdBetween(10L, 50L, pageable);

        assertThat(result.getSize()).isEqualTo(10);
    }

    @Transactional
    @Test
    void testQueryMethodDelete() {
        memoRepository.deleteMemoByIdLessThan(10L);
        List<Memo> result = memoRepository.findAll();

        assertThat(result.size()).isEqualTo(100);
    }

    @Test
    void testQueryAnnotation() {
        List<Memo> listDesc = memoRepository.getListDesc();

        assertThat(listDesc.size()).isEqualTo(100);
    }
}
