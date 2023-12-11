package com.jpa.yanus.controller;

import com.jpa.yanus.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/")
public class MainController {

    private final MemberService memberService;

    @GetMapping("")
    public String goMain(){
        return "main";
    }
}
