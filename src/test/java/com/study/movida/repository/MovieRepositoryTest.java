package com.study.movida.repository;

import com.study.movida.entity.Movie;
import com.study.movida.entity.MovieImage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MovieRepositoryTest {
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieImageRepository movieImageRepository;

    @Commit
    @Transactional
    @Test
    void insertMovies() {
        movieRepository.deleteAll();
        movieImageRepository.deleteAll();
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Movie movie = Movie.builder()
                    .title("Movie .. " + i).build();

            movieRepository.save(movie);

            int count = (int)(Math.random() * 5) + 1;

            for(int j = 0; j < count; j++) {
                MovieImage movieImage = MovieImage.builder()
                        .uuid(UUID.randomUUID().toString())
                        .movie(movie)
                        .imgName("test"+ j + ".jpg").build();

                movieImageRepository.save(movieImage);
            }
        });
    }

    @Test
    void testListPage() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "mno"));

        Page<Object[]> result = movieRepository.getListPage(pageable);

        for (Object[] objects : result.getContent()) {
            System.out.println(Arrays.toString(objects));
        }

    }

    @Test
    void testGetMovieWithAll() {

        List<Object[]> movieWithAll = movieRepository.getMovieWithAll(94L);
        System.out.println("movieWithAll = " + movieWithAll);
        for (Object[] objects : movieWithAll) {
            System.out.println("objects = " + Arrays.toString(objects));
        }
    }
}
