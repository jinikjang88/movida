package com.study.movida.controller;

import com.study.movida.dto.MovieDto;
import com.study.movida.dto.PageRequestDto;
import com.study.movida.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/movie")
@Slf4j
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService; //final

    @GetMapping("/register")
    public void register(){

    }

    @PostMapping("/register")
    public String register(MovieDto movieDto, RedirectAttributes redirectAttributes){
        log.info("movieDTO: " + movieDto);

        Long mno = movieService.register(movieDto);

        redirectAttributes.addFlashAttribute("msg", mno);

        return "redirect:/movie/list";
    }

    @GetMapping("/list")
    public void list(PageRequestDto pageRequestDto, Model model) {
        log.info("### pageRequestDto : {}", pageRequestDto);

        model.addAttribute("result", movieService.getList(pageRequestDto));
    }

    @GetMapping({"/read", "/modify"})
    public void read(Long mno, @ModelAttribute("requestDto") PageRequestDto pageRequestDto, Model model) {
        log.info("### mno : {}", mno);
        MovieDto movieDto = movieService.getMovie(mno);
        model.addAttribute("dto", movieDto);
    }

//    @GetMapping("/list")
//    public void list(PageRequestDTO pageRequestDTO, Model model){
//
//        log.info("pageRequestDTO: " + pageRequestDTO);
//
//
//        model.addAttribute("result", movieService.getList(pageRequestDTO));
//
//    }
//
//    @GetMapping({"/read", "/modify"})
//    public void read(long mno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model ){
//
//        log.info("mno: " + mno);
//
//        MovieDTO movieDTO = movieService.getMovie(mno);
//
//        model.addAttribute("dto", movieDTO);
//
//    }
//

}
