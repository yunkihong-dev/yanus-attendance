package com.jpa.yanus.controller;

import com.jpa.yanus.domain.MemberDTO;
import com.jpa.yanus.entity.Member;
import com.jpa.yanus.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.Optional;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/")
public class MainController {

    private final MemberService memberService;

    @GetMapping("")
    public String goMain(@SessionAttribute(name = "id", required = false) Long id, Model model){
        final Optional<Member> foundMember = memberService.getMemberById(id);
        if (foundMember.isPresent()){
            model.addAttribute("member", new MemberDTO());
            return "main";
        }else{
            return "main";
        }
    }
}
