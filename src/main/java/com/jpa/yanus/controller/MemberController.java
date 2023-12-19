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
    public String login(Model model, HttpServletRequest request) {
        HttpSession httpSession = request.getSession(false);

        // 세션이 null이거나 세션의 "id" 속성이 null인 경우 로그인 페이지로 이동
        if (httpSession == null || httpSession.getAttribute("id") == null) {
            model.addAttribute("member", new MemberDTO());
            return "member/login";  // 리디렉션 대신 뷰 이름 직접 반환
        } else {
            return "redirect:/";  // 이미 로그인된 사용자는 메인 페이지로 리디렉션
        }
    }


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
    public String attendance(Model model, HttpServletRequest request) {
        HttpSession httpSession = request.getSession(false);

        // 세션이 null인 경우 로그인 페이지로 리디렉션
        if (httpSession == null) {
            return "redirect:/member/login";
        }

        Long id = (Long) httpSession.getAttribute("id");

        // id가 null인 경우 로그인 페이지로 리디렉션
        if (id == null) {
            return "redirect:/member/login";
        }

        Optional<Member> member = memberService.getMemberById(id);
        if (member.isPresent()) {
            model.addAttribute("member", member.get());
            return "member/attendance"; // 직접 뷰 이름 반환
        } else {
            return "redirect:/member/login";
        }
    }


    @GetMapping("logout")
    public RedirectView logout(HttpSession session){
        session.invalidate();
        return new RedirectView("/member/login");
    }



}
