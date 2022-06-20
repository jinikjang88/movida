package com.study.movida.service;

import com.study.movida.dto.MovieDto;
import com.study.movida.dto.MovieImageDto;
import com.study.movida.dto.PageRequestDto;
import com.study.movida.dto.PageResultDto;
import com.study.movida.entity.Movie;
import com.study.movida.entity.MovieImage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface MovieService {

   Long register(MovieDto movieDto);

   PageResultDto<MovieDto, Object[]> getList(PageRequestDto pageRequestDto);

   MovieDto getMovie(Long mno);

   default MovieDto entitiesToDto(Movie movie, List<MovieImage> movieImageList, Double avg, Long reviewCnt) {
       MovieDto movieDto = MovieDto.builder()
               .mno(movie.getMno())
               .title(movie.getTitle())
               .regDate(movie.getRegDate())
               .modDate(movie.getModDate())
               .build();

       List<MovieImageDto> movieImageDtoList = movieImageList.stream().map(
               movieImage -> MovieImageDto.builder()
                       .imgName(movieImage.getImgName())
                       .path(movieImage.getPath())
                       .uuid(movieImage.getUuid())
                       .build()).collect(Collectors.toList());

       movieDto.setImageDtoList(movieImageDtoList);
       movieDto.setAvg(avg);
       movieDto.setReviewCnt(reviewCnt.intValue());
       return movieDto;
   }

   default Map<String, Object> dtoToEntity(MovieDto movieDto) {
       Map<String, Object> entityMap = new HashMap<>();

       Movie movie = Movie.builder()
               .mno(movieDto.getMno())
               .title(movieDto.getTitle())
               .build();

       entityMap.put("movie", movie);

       List<MovieImageDto> imageDtoList = movieDto.getImageDtoList();

       //process MovieImageDto
       if (imageDtoList != null && imageDtoList.size() > 0 ) {
           List<MovieImage> movieImageList = imageDtoList.stream()
                   .map(movieImageDto -> {
                       return MovieImage.builder()
                               .path(movieImageDto.getPath())
                               .imgName(movieImageDto.getImgName())
                               .uuid(movieImageDto.getUuid())
                               .movie(movie)
                               .build();
                   }).collect(Collectors.toList());

           entityMap.put("imgList", movieImageList);
       }

       return entityMap;
   }
}
