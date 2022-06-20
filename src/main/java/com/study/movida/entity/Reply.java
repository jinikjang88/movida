package com.study.movida.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "board") //ToString 주의.. 이렇게 하지 않으면 계속 연관관계 참조가 도돌이표된다.
public class Reply extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    private String text;

    private String replyer;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    // BOARD 와 연관관계는 아직 작성하지 않음
}
