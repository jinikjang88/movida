package com.study.movida.service;

import com.study.movida.dto.ReviewDto;
import com.study.movida.entity.Movie;
import com.study.movida.entity.Review;
import com.study.movida.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    @Override
    public List<ReviewDto> getListOfMovie(Long mno) {
        Movie movie = Movie.builder().mno(mno).build();
        List<Review> result = reviewRepository.findByMovie(movie);

        return result.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    @Override
    public Long register(ReviewDto reviewDto) {
        log.info("#### reviewDto : {}", reviewDto);
        Review movieReview = dtoToEntity(reviewDto);
        reviewRepository.save(movieReview);
        return movieReview.getReviewNum();
    }

    @Override
    public void modify(ReviewDto reviewDto) {
        Optional<Review> result = reviewRepository.findById(reviewDto.getReviewNum());

        if(result.isPresent()) {
            Review review = result.get();
            review.changeGrade(reviewDto.getGrade());
            review.changeText(reviewDto.getText());

            reviewRepository.save(review);
        }

    }

    @Override
    public void remove(Long reviewNum) {
        reviewRepository.deleteById(reviewNum);

    }
}
