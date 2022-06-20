package com.study.movida.repository;

import com.study.movida.entity.Board;
import com.study.movida.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @Modifying
    @Query("DELETE FROM Reply r WHERE r.board.bno = :bno")
    void deleteByBno(@Param("bno") Long bno);

    List<Reply> getRepliesByBoardOrderByRno(Board board);
}
