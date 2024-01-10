package com.jpa.yanus.controller;

import com.jpa.yanus.domain.AttendanceMemberJoinDTO;
import com.jpa.yanus.domain.NoWorkDTO;
import com.jpa.yanus.entity.Member;
import com.jpa.yanus.service.AttendanceService;
import com.jpa.yanus.service.MemberService;
import com.jpa.yanus.service.NoWorkService;
import com.jpa.yanus.type.MemberType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/*")
public class AdminController {
    private final MemberService memberService;
    private final AttendanceService attendanceService;
    private final NoWorkService noWorkService;

    @GetMapping("adminpage")
    public String goAdmin(Model model, HttpServletRequest request) {
        HttpSession httpSession = request.getSession(false);
        // 세션이 null인 경우를 먼저 확인
        if (httpSession == null) {
            return "redirect:/"; // 세션이 없으면 메인 페이지로 리디렉션
        }
        log.info(httpSession.getId());
        Long memberId = (Long) httpSession.getAttribute("id");

        // memberId가 null인 경우도 처리 (옵셔널)
        if (memberId == null) {
            log.info(memberId.toString());
            return "redirect:/"; // 회원 ID가 없으면 메인 페이지로 리디렉션
        }
        log.info(memberId.toString());
        Optional<Member> member = memberService.getMemberById(memberId);
        log.info(member.toString());
        if (member.isPresent()) {
            if (member.get().getMemberType().equals(MemberType.ADMIN) || member.get().getMemberType().equals(MemberType.SUPERADMIN)) {

                List<Member> memberList = memberService.getAllMembers();
                if (memberList == null) {
                    memberList = new ArrayList<>(); // 빈 리스트로 초기화
                }
                model.addAttribute("memberList", memberList);


                List<NoWorkDTO> noWorkList = noWorkService.getAllNoWorkWithMemberName();
                log.info(noWorkList.toString());
                if(noWorkList == null){
                    noWorkList = new ArrayList<>();
                }
                model.addAttribute("noWorkList",noWorkList);

                if(member.get().getMemberType().equals(MemberType.SUPERADMIN)){
                    List<AttendanceMemberJoinDTO> attendanceList = attendanceService.findAllAttendanceToday();
                    log.info(attendanceList.toString());
                    model.addAttribute("attendanceList",attendanceList);

                }else{
                    List<AttendanceMemberJoinDTO> attendanceList = attendanceService.findMyTeamAttendanceToday(member.get().getMemberTeamNum());
                    model.addAttribute("attendanceList",attendanceList);
                }

                model.addAttribute("member",member.get());
                return "admin/adminpage";
            } else {
                return "redirect:/";
            }
        } else {
            return "redirect:/";
        }
    }




}
