package com.study.movida.repository;

import com.study.movida.entity.Member;
import com.study.movida.entity.Movie;
import com.study.movida.entity.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    void insertMovieReviews() {
        reviewRepository.deleteAll();
        IntStream.rangeClosed(1, 200).forEach(i -> {

            Long mno = (long)(Math.random() * 100) + 1;

            Long mid = (long)(Math.random() * 100) + 1;

            Member member = Member.builder().mid(mid).build();

            Review movieReview = Review.builder()
                    .member(member)
                    .movie(Movie.builder().mno(mno).build())
                    .grade((int)(Math.random() * 5) + 1)
                    .text("이 영화에 대한 느낌.. " + i)
                    .build();

            reviewRepository.save(movieReview);
        });
    }

    @Test
    void testGetMovieReviews() {
        Movie movie = Movie.builder().mno(100L).build();

        List<Review> result = reviewRepository.findByMovie(movie);

        for (Review review : result) {
            System.out.println(review.getReviewNum());
            System.out.println(review.getGrade());
            System.out.println(review.getText());
            System.out.println(review.getMember().getEmail());
        }
    }

}
