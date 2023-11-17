package com.jpa.yanus.controller;

import com.jpa.yanus.domain.MemberDTO;
import com.jpa.yanus.entity.Member;
import com.jpa.yanus.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/member/*")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("login")
    public void login(Model model){ model.addAttribute("member", new MemberDTO());}

    @PostMapping("login")
    public RedirectView login(Member member, HttpSession httpSession, HttpServletRequest request){
        final Optional<Member> foundMember = memberService.getMember(member.getMemberId(),member.getMemberPassword());
        if(foundMember.isPresent()){
            log.info("로그인 성공!");
            httpSession.setAttribute("id", foundMember.get().getId());

            return new RedirectView("/member/attendance");
        }
        else {
            log.info("로그인 실패!");
            return new RedirectView("/member/login");
        }

    }
    @GetMapping("attendance")
    public void attendance(@SessionAttribute(name = "id", required = false) Long id,Model model){
        Member member = memberService.getMemberById(id).get();

        model.addAttribute("member",member);
    }

    @GetMapping("logout")
    public RedirectView logout(HttpSession session){
        session.invalidate();
        return new RedirectView("/member/login");
    }



}
