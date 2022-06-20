//package com.study.movida.repository;
//
//import antlr.collections.impl.IntRange;
//import com.study.movida.entity.Board;
//import com.study.movida.entity.Reply;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.IntStream;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//class ReplyRepositoryTest {
//
//    @Autowired
//    private ReplyRepository replyRepository;
//
//    @Autowired
//    private BoardRepository boardRepository;
//
//
//    @Test
//    void insertReply() {
//        IntStream.rangeClosed(1, 300).forEach(i -> {
//
//            long bno = (long) (Math.random() * 100) + 1;
//            Board board = Board.builder()
//                    .bno(bno).build();
//
//            boardRepository.save(board);
//
//            Reply reply = Reply.builder()
//                    .text("reply " + i)
//                    .board(board)
//                    .replyer("guest")
//                    .build();
//
//            replyRepository.save(reply);
//        });
//    }
//
//    @Test
//    void readReply1() {
//        Optional<Reply> result = replyRepository.findById(1L);
//
//        if (result.isPresent()) {
//            Reply reply = result.get();
//            System.out.println(reply);
//            System.out.println(reply.getBoard());
//        }
//    }
//
//    @Test
//    void testListByBoard() {
//        List<Reply> replyList = replyRepository.getRepliesByBoardOrderByRno(Board.builder().bno(97L).build());
//        replyList.forEach(System.out::println);
//    }
//
//}
