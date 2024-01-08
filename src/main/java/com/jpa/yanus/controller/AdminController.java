package com.jpa.yanus.controller;

import com.jpa.yanus.domain.AttendanceDTO;
import com.jpa.yanus.domain.AttendanceMemberJoinDTO;
import com.jpa.yanus.domain.NoWorkDTO;
import com.jpa.yanus.entity.Attendance;
import com.jpa.yanus.entity.Member;
import com.jpa.yanus.entity.NoWork;
import com.jpa.yanus.service.AttendanceService;
import com.jpa.yanus.service.MemberService;
import com.jpa.yanus.service.NoWorkService;
import com.jpa.yanus.type.MemberType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
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

    @Autowired
    private NoWorkService noWorkService;

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
            if (member.get().getMemberType().equals(MemberType.ADMIN) || member.get().getMemberType().equals(MemberType.SUPERADMIN)) {

                List<Member> memberList = memberService.getAllMembers();

                model.addAttribute("memberList",memberList);

                List<NoWorkDTO> noWorkList = noWorkService.getAllNoWorkWithMemberName();

                model.addAttribute("noWorkList",noWorkList);

                if(member.get().getMemberType().equals(MemberType.SUPERADMIN)){
                    List<AttendanceMemberJoinDTO> attendanceList = attendanceService.findAllAttendanceToday();
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
