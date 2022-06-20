package com.study.movida.repository;

import com.study.movida.entity.Memo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MemoRepository extends JpaRepository<Memo, Long> {

    List<Memo> findByIdBetweenOrderByIdDesc(Long from, Long to);

    Page<Memo> findByIdBetween(Long from, Long to, Pageable pageable);

    void deleteMemoByIdLessThan(Long memoId);

    @Query("select m from Memo m order by m.id desc")
    List<Memo> getListDesc();

    @Transactional
    @Modifying
    @Query("update Memo m set m.memoText = :memoText where m.id = :id")
    int updateMemoText(@Param("id") Long id, @Param("memoText") String memoText);

    @Transactional
    @Modifying
    @Query("update Memo m set m.memoText = :#{#param.memoText} where m.id = :#{#param.id}")
    int updateMemoTextBeanParam(@Param("param") Memo memo);


    @Query(value = "select m from Memo m where m.id > :id",
    countQuery = "select count(m) from Memo m where m.id > :id")
    Page<Memo> getListWithQuery(Long id, Pageable pageable);


    @Query(value = "select m.id, m.memoText, CURRENT_DATE  from Memo m where m.id > :id",
    countQuery = "select count(m) from Memo m where m.id > :id")
    Page<Object[]> getListWithQueryObject(Long id, Pageable pageable);


    @Query(value = "select * from memo where id > 0 ", nativeQuery = true)
    List<Object[]> getNativeResult();

}
