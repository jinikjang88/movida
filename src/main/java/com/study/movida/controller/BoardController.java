package com.study.movida.controller;

import com.study.movida.dto.BoardDto;
import com.study.movida.dto.PageRequestDto;
import com.study.movida.service.BoardService;
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
@RequestMapping("/board")
@Slf4j
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public void list(PageRequestDto pageRequestDto, Model model) {
        log.info("### list... {}", pageRequestDto);

        model.addAttribute("result", boardService.getList(pageRequestDto));
    }

    @GetMapping("/register")
    public void register() {
        log.info("#### register get.. ");
    }

    @PostMapping("/register")
    public String register(BoardDto boardDto, RedirectAttributes redirectAttributes) {
        log.info("### dto : {}", boardDto);

        Long bno = boardService.register(boardDto);

        log.info("### bNo : {} ", bno);
        redirectAttributes.addFlashAttribute("msg", bno);
        return "redirect:/board/list";
    }

    @GetMapping({"/read", "/modify"})
    public void read(@ModelAttribute("requestDto") PageRequestDto pageRequestDto, Long bno, Model model) {
        log.info("## bno : {} " , bno);
        BoardDto boardDto = boardService.get(bno);
        log.info("### boardDto : {}", boardDto);
        model.addAttribute("dto", boardDto);
    }

    @PostMapping("/remove")
    public String remove(Long bno, RedirectAttributes redirectAttributes) {
        log.info("### bno : {}", bno);
        boardService.removeWithReplies(bno);
        redirectAttributes.addFlashAttribute("msg", bno);
        return "redirect:/board/list";
    }

    @PostMapping("/modify")
    public String modify(BoardDto dto, @ModelAttribute("requestDto") PageRequestDto pageRequestDto, RedirectAttributes redirectAttributes) {
        log.info("### post modeify");
        log.info("dto :: {} ", dto);

        boardService.modify(dto);

        redirectAttributes.addAttribute("page", pageRequestDto.getPage());
        redirectAttributes.addAttribute("type", pageRequestDto.getType());
        redirectAttributes.addAttribute("keyword", pageRequestDto.getKeyword());
        redirectAttributes.addAttribute("bno", dto.getBno());

        return "redirect:/board/read";
    }

}
