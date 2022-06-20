package com.study.movida.service;

import com.study.movida.dto.ReviewDto;
import com.study.movida.entity.Member;
import com.study.movida.entity.Movie;
import com.study.movida.entity.Review;

import java.util.List;

public interface ReviewService {

    List<ReviewDto> getListOfMovie(Long mno);

    Long register(ReviewDto reviewDto);

    void modify(ReviewDto reviewDto);

    void remove(Long reviewNum);

    default Review dtoToEntity(ReviewDto reviewDto) {
        return Review.builder()
                .reviewNum(reviewDto.getReviewNum())
                .movie(Movie.builder().mno(reviewDto.getMno()).build())
                .member(Member.builder().mid(reviewDto.getMid()).build())
                .grade(reviewDto.getGrade())
                .text(reviewDto.getText())
                .build();
    }

    default ReviewDto entityToDto(Review review) {
        return ReviewDto.builder()
                .reviewNum(review.getReviewNum())
                .mno(review.getMovie().getMno())
                .mid(review.getMember().getMid())
                .nickName(review.getMember().getNickName())
                .email(review.getMember().getEmail())
                .grade(review.getGrade())
                .text(review.getText())
                .regDate(review.getRegDate())
                .modDate(review.getModDate())
                .build();
    }
}
