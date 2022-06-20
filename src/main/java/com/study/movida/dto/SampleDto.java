package com.study.movida.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder(toBuilder = true)
public class SampleDto {

    private Long sNo;
    private String first;
    private String last;
    private LocalDateTime regTime;
}
