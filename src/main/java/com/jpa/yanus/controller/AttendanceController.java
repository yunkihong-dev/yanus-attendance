package com.jpa.yanus.controller;

import com.jpa.yanus.domain.NoWorkWithOutMemberDTO;
import com.jpa.yanus.entity.Member;
import com.jpa.yanus.repository.MemberRepository;
import com.jpa.yanus.service.AttendanceService;
import com.jpa.yanus.service.MemberService;
import com.jpa.yanus.service.NoWorkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/attendance/*")
public class AttendanceController {

    private final AttendanceService attendanceService;
    private final MemberService memberService;
    private final NoWorkService noWorkService;

    @GetMapping("")
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
            List<NoWorkWithOutMemberDTO> noWorkList =  noWorkService.getMyAllNoWork(member.get().getId());

            model.addAttribute("noWorkList",noWorkList);

            model.addAttribute("member", member.get());


            return "member/attendance"; // 직접 뷰 이름 반환
        } else {
            return "redirect:/member/login";
        }
    }

}