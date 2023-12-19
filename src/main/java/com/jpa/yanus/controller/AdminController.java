package com.jpa.yanus.controller;

import com.jpa.yanus.entity.Member;
import com.jpa.yanus.service.AttendanceService;
import com.jpa.yanus.service.MemberService;
import com.jpa.yanus.type.MemberType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/*")
public class AdminController {
    @Autowired
    private MemberService memberService;

    @Autowired
    private AttendanceService attendanceService;

    @GetMapping("adminpage")
    public String goAdmin(Model model, HttpServletRequest request) {
        HttpSession httpSession = request.getSession(false);

        // 세션이 null인 경우를 먼저 확인
        if (httpSession == null) {
            return "redirect:/"; // 세션이 없으면 메인 페이지로 리디렉션
        }

        Long memberId = (Long) httpSession.getAttribute("id");

        // memberId가 null인 경우도 처리 (옵셔널)
        if (memberId == null) {
            return "redirect:/"; // 회원 ID가 없으면 메인 페이지로 리디렉션
        }

        Optional<Member> member = memberService.getMemberById(memberId);
        if (member.isPresent()) {
            if (member.get().getMemberType().equals(MemberType.ADMIN)) {
                return "admin/adminpage";
            } else {
                return "redirect:/";
            }
        } else {
            return "redirect:/";
        }
    }



}
