package com.study.movida.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {

    private Long reviewNum;

    private Long mno;

    private Long mid;

    private String nickName;

    private String email;

    private int grade;

    private String text;

    private LocalDateTime regDate, modDate;
}
