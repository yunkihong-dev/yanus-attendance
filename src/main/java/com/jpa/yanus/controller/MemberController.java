package com.jpa.yanus.controller;

import com.jpa.yanus.entity.Member;
import com.jpa.yanus.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/member/*")
public class MemberController {

    private final MemberService memberService;


    @PostMapping("/member/login")
    public ResponseEntity<?> login(@RequestBody Member member, HttpSession httpSession) {
        final Optional<Member> foundMember = memberService.getMember(member.getMemberId(), member.getMemberPassword());

        if (foundMember.isPresent()) {
            httpSession.setAttribute("id", foundMember.get().getId());
            return ResponseEntity.ok().body(Map.of("message", "로그인 성공!", "status", "success"));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "회원을 찾을 수 없음", "status", "fail"));
        }
    }

    @GetMapping("logout")
    public RedirectView logout(HttpSession session){
        session.invalidate();
        return new RedirectView("/");
    }

//    @PostMapping("/member/deleteMembers")
//    public ResponseEntity<?> getMembersDelete(@RequestBody List<Long> memberIds){
//        final List<Optional<Member>> memberList = memberService
//
//
//
//
//
//    }

}
