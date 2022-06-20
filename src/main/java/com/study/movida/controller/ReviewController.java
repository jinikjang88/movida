package com.study.movida.controller;

import com.study.movida.dto.ReviewDto;
import com.study.movida.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@Slf4j
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{mno}/all")
    public ResponseEntity<List<ReviewDto>> getList(@PathVariable("mno") Long mno) {
        log.info("#### --- list ----#### ");

        List<ReviewDto> listOfMovie = reviewService.getListOfMovie(mno);

        return new ResponseEntity<>(listOfMovie, HttpStatus.OK);
    }

    @PostMapping("/{mno}")
    public ResponseEntity<Long> addReview(@RequestBody ReviewDto reviewDto) {
        log.info("### add Review ###");

        Long reviewNum = reviewService.register(reviewDto);
        return new ResponseEntity<>(reviewNum, HttpStatus.OK);
    }

    @PutMapping("/{mno}/{reviewNum}")
    public ResponseEntity<Long> modifyReview(@PathVariable Long mno, @PathVariable Long reviewNum, @RequestBody ReviewDto reviewDto) {
        log.info("### modify Review ###");

        reviewService.modify(reviewDto);

        return new ResponseEntity<>(reviewNum, HttpStatus.OK);
    }

    @DeleteMapping("/{mno}/{reviewNum}")
    public ResponseEntity<Long> removeReview(@PathVariable Long mno, @PathVariable Long reviewNum) {
        log.info("### delete Review ###");

        reviewService.remove(reviewNum);
        return new ResponseEntity<>(reviewNum, HttpStatus.OK);
    }


}
