package com.study.movida.repository;

import com.study.movida.entity.Member;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Commit
    @Transactional
    @Test
    @Order(0)
    public void insertMember() {
        memberRepository.deleteAll();
        List<Member> memberList = IntStream.rangeClosed(1, 100)
                .mapToObj(i -> Member.builder()
                        .email("user" + i + "@abc.com")
                        .password("1111")
                        .name("user" + i)
                        .nickName("reviewer" + i)
                        .build())
                .collect(Collectors.toList());
        memberRepository.saveAll(memberList);
    }

    @Transactional
    @Test
    @Order(1)
    void testDeleteMember() {
        Long mid = 1L;
        Member member = Member.builder().mid(mid).build();
        reviewRepository.deleteByMember(member);
        memberRepository.deleteById(mid);
    }
}
