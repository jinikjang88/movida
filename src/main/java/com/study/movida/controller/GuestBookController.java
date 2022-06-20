package com.study.movida.controller;

import com.study.movida.dto.GuestBookDto;
import com.study.movida.dto.PageRequestDto;
import com.study.movida.service.GuestBookService;
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
@RequestMapping("/guestbook")
@Slf4j
@RequiredArgsConstructor
public class GuestBookController {

    private final GuestBookService guestBookService;

    @GetMapping("/")
    public String index() {
        return "redirect:/guestbook/list";
    }

    @GetMapping("/list")
    public void list(PageRequestDto pageRequestDto, Model model) {
        log.info("#### list ... {} ", pageRequestDto);
        model.addAttribute("result", guestBookService.getList(pageRequestDto));
    }

//    @GetMapping("/read")
    @GetMapping({"/read", "/modify"})
    public void read(Long gno, @ModelAttribute("requestDto") PageRequestDto requestDto, Model model) {
        log.info("## gno {}", gno);
        GuestBookDto dto = guestBookService.read(gno);
        model.addAttribute("dto", dto);
    }

    @GetMapping("/register")
    public void register() {
        log.info("## register get..");
    }

    @PostMapping("/register")
    public String registerPost(GuestBookDto dto, RedirectAttributes redirectAttributes) {
        log.info("## dto.. {} ", dto);

        Long gno = guestBookService.register(dto);

        redirectAttributes.addFlashAttribute("msg", gno);
        return "redirect:/guestbook/list";
    }

    @PostMapping("/remove")
    public String remove(Long gno, RedirectAttributes redirectAttributes) {
        log.info("## gno : {}" , gno);

        guestBookService.remove(gno);

        redirectAttributes.addFlashAttribute("msg", gno);
        return "redirect:/guestbook/list";
    }

    @PostMapping("/modify")
    public String modify(GuestBookDto dto, @ModelAttribute("requestDto") PageRequestDto requestDto,
                         RedirectAttributes redirectAttributes) {
        log.info("## post Modify... ");
        log.info("## dto : {} ", dto);

        guestBookService.modify(dto);

        redirectAttributes.addAttribute("page", requestDto.getPage());
        redirectAttributes.addAttribute("type", requestDto.getType());
        redirectAttributes.addAttribute("keyword", requestDto.getKeyword());
        redirectAttributes.addAttribute("gno", dto.getGno());

        return "redirect:/guestbook/read";
    }



}
