package com.jpa.yanus.controller;

import com.jpa.yanus.domain.MemberTeamUpdateDTO;
import com.jpa.yanus.entity.Member;
import com.jpa.yanus.service.MemberService;
import com.jpa.yanus.type.StatusType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
            if(foundMember.get().getMemberStatus().equals(StatusType.UNABLE)){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message","로그인이 불가한 계정입니다. 관리자에게 문의하세요","status","fail"));
            }
            log.info("로그인 성공");
            return ResponseEntity.ok().body(Map.of("message", "로그인 성공!", "status", "success"));
        } else {
            log.info("로그인 실패");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "회원을 찾을 수 없음", "status", "fail"));
        }
    }

    @GetMapping("logout")
    public RedirectView logout(HttpSession session){
        session.invalidate();
        return new RedirectView("/");
    }

    @PostMapping("/deleteMembers")
    public ResponseEntity<?> getMembersDelete(@RequestBody List<Long> memberIds) {
        try {
            memberService.softDeleteByMemberIds(memberIds);
            return ResponseEntity.ok(Map.of("message", "탈퇴 처리 완료", "status", "success"));
        } catch (Exception e) {
            log.error("회원 삭제 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "회원 삭제 중 오류가 발생했습니다.", "status", "fail"));
        }
    }

    @PostMapping("/changeTeam")
    public ResponseEntity<?> changeTeam(@RequestBody Map<String, List<MemberTeamUpdateDTO>> updatesMap) {
        List<MemberTeamUpdateDTO> updates = updatesMap.get("updates");
        try {
            memberService.changeTeam(updates);
            return ResponseEntity.ok(Map.of("message", "변경 완료", "status", "success"));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(Map.of("message", "500 error", "status", "fail"));
        }
    }

}
