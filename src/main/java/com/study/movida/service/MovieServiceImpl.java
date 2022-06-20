package com.study.movida.service;

import com.study.movida.dto.MovieDto;
import com.study.movida.dto.PageRequestDto;
import com.study.movida.dto.PageResultDto;
import com.study.movida.entity.Movie;
import com.study.movida.entity.MovieImage;
import com.study.movida.repository.MovieImageRepository;
import com.study.movida.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
@Slf4j
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final MovieImageRepository movieImageRepository;

    @Transactional
    @Override
    public Long register(MovieDto movieDto) {
        Map<String, Object> entityMap = dtoToEntity(movieDto);
        Movie movie = (Movie) entityMap.get("movie");

        List<MovieImage> movieImageList = (List<MovieImage>) entityMap.get("imgList");

        movieRepository.save(movie);

        if (movieImageList != null && !movieImageList.isEmpty()) {
            movieImageRepository.saveAll(movieImageList);
        }

        return movie.getMno();
    }

    @Override
    public PageResultDto<MovieDto, Object[]> getList(PageRequestDto pageRequestDto) {
        Pageable pageable = pageRequestDto.getPageable(Sort.by("mno").descending());
        Page<Object[]> result = movieRepository.getListPage(pageable);

        Function<Object[], MovieDto> fn = (arr -> entitiesToDto(
                (Movie)arr[0],
                Arrays.asList((MovieImage)arr[1]),
                (Double) arr[2],
                (Long) arr[3]
        ));

        return new PageResultDto<>(result, fn);
    }

    @Override
    public MovieDto getMovie(Long mno) {
        List<Object[]> result = movieRepository.getMovieWithAll(mno);

        Movie movie = (Movie) result.get(0)[0];

        List<MovieImage> movieImageList = new ArrayList<>();

        result.forEach(arr -> {
            MovieImage movieImage = (MovieImage) arr[1];
            movieImageList.add(movieImage);
        });

        Double avg = (Double) result.get(0)[2];
        Long reviewCnt = (Long) result.get(0)[3];

        return entitiesToDto(movie, movieImageList, avg, reviewCnt);
    }
}
