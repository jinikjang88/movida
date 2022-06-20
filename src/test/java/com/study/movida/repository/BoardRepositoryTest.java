//package com.study.movida.repository;
//
//import com.study.movida.entity.Board;
//import com.study.movida.entity.Member;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.IntStream;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//class BoardRepositoryTest {
//
//    @Autowired
//    private BoardRepository boardRepository;
//    @Autowired
//    private MemberRepository memberRepository;
//
//    @Test
//    void insertBoard() {
//        IntStream.rangeClosed(1, 100).forEach(i -> {
//            Member member = Member.builder()
//                    .email("user"+i+"@abc.com")
//                    .build();
//
//            Board board = Board.builder()
//                    .title("TITLE " + i)
//                    .content("CONTENT " + i)
//                    .writer(member)
//                    .build();
//            boardRepository.save(board);
//        });
//    }
//
//    @Transactional
//    @Test
//    void testRead1() {
//        Optional<Board> result = boardRepository.findById(1L);
//
//        if (result.isPresent()) {
//            Board board = result.get();
//            System.out.println(board);
//            System.out.println(board.getWriter());
//        }
//    }
//
//    @Test
//    public void testReadWithWriter() {
//        Object result = boardRepository.getBoardWithWriter(100L);
//
//        Object[] arr = (Object[]) result;
//        System.out.println("--------------");
//        System.out.println(Arrays.toString(arr));
//    }
//
//    @Test
//    void testGetBoardWithReply() {
//        List<Object[]> result = boardRepository.getBoardWithReply(100L);
//        for (Object[] arr : result) {
//            System.out.println(Arrays.toString(arr));
//        }
//    }
//
//    @Test
//    void testWithReplyCount() {
//        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
//
//        Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageable);
//
//        result.get().forEach(row -> System.out.println(Arrays.toString(row)));
//    }
//
//    @Test
//    void testRead3() {
//        Object result = boardRepository.getBoardByBno(100L);
//        Object[] arr = (Object[]) result;
//        System.out.println(Arrays.toString(arr));
//    }
//
//    @Test
//    void testSearch1() {
//        boardRepository.search1();
//    }
//
//    @Test
//    void testSearchPage() {
//        String type = "t";
//        String keyword = "1";
//        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending()
//                .and(Sort.by("title").ascending()));
//        boardRepository.searchPage(type, keyword, pageable);
//    }
//
//}
