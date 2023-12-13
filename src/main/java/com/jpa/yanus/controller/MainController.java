package com.jpa.yanus.controller;

import com.jpa.yanus.entity.Member;
import com.jpa.yanus.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/")
public class MainController {

    private final MemberService memberService;

    @GetMapping("")
    public String goMain(HttpSession session, Model model){

        Long memberId = (Long) session.getAttribute("id");

        if(memberId == null){
            return "main";
        } else {
            Optional<Member> member = memberService.getMemberById(memberId);
            if(member.isPresent()) {
                model.addAttribute("member", member.get());
            } else {
                // member가 없으면 null을 모델에 추가하거나, 다른 처리를 할 수 있음
                model.addAttribute("member", null);
            }
            return "main";
        }
    }

}
