package com.study.movida.controller;

import com.study.movida.dto.ReplyDto;
import com.study.movida.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/replies")
@Slf4j
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @GetMapping(value = "/board/{bno}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReplyDto>> getListByBoard(@PathVariable("bno") Long bno) {
        log.info("## bno : {} " , bno);
        return new ResponseEntity<>( replyService.getList(bno), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Long> register(@RequestBody ReplyDto replyDto) {
        log.info("### reply register : {}", replyDto);
        Long rno = replyService.register(replyDto);

        return new ResponseEntity<>(rno, HttpStatus.OK);
    }

    @DeleteMapping("/{rno}")
    public ResponseEntity<String> remove(@PathVariable("rno") Long rno) {
        log.info("## remove rno : {}", rno);
        replyService.remove(rno);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @PutMapping("/{rno}")
    public ResponseEntity<String> modify(@RequestBody ReplyDto replyDto) {
        log.info("## replyDtp {} ", replyDto);

        replyService.modify(replyDto);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }


}
