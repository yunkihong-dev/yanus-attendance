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
import org.springframework.web.bind.annotation.RequestParam;

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
    public String goAdminPage(
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to,
            @RequestParam(required = false) List<Long> memberIds,
            Model model,
            HttpServletRequest request) {

        HttpSession httpSession = request.getSession(false);

        Long memberId = (Long) httpSession.getAttribute("id");

        Optional<Member> member = memberService.getMemberById(memberId);

        Member currentMember = member.get();

        if (!currentMember.getMemberType().equals(MemberType.ADMIN) &&
                !currentMember.getMemberType().equals(MemberType.SUPERADMIN)) {
            return "redirect:/"; // 관리자 권한이 없는 경우 리디렉션
        }

        // 기본 데이터 로드
        List<Member> memberList = memberService.getAllMembers();
        if (memberList == null) {
            memberList = new ArrayList<>();
        }
        model.addAttribute("memberList", memberList);

        List<NoWorkDTO> noWorkList = noWorkService.getAllNoWorkWithMemberName();
        if (noWorkList == null) {
            noWorkList = new ArrayList<>();
        }
        model.addAttribute("noWorkList", noWorkList);

        List<AttendanceMemberJoinDTO> attendanceList;
        if (currentMember.getMemberType().equals(MemberType.SUPERADMIN)) {
            attendanceList = attendanceService.findAllAttendanceToday();
        } else {
            attendanceList = attendanceService.findMyTeamAttendanceToday(currentMember.getMemberTeamNum());
        }
        model.addAttribute("attendanceList", attendanceList);
        model.addAttribute("member", currentMember);

        // 쿼리스트링 유무에 따라 다른 로직 처리
        if (from != null && to != null && memberIds != null) {
            log.info("쿼리스트링 있음 - from: {}, to: {}, memberIds: {}", from, to, memberIds);


            // 예: 날짜 범위와 특정 멤버에 따른 필터링 로직 추가
            List<AttendanceMemberJoinDTO> filteredAttendanceList =
                    attendanceService.findAttendanceByDateRangeAndMembers(from, to, memberIds);
            log.info("필터링된 결과: {}", filteredAttendanceList); // 결과 로그로 확인

            model.addAttribute("attendanceList", filteredAttendanceList);
            return "admin/adminpage"; // 필터링된 페이지로 이동
        }

        log.info("쿼리스트링 없음 - 기본 페이지 렌더링");
        return "admin/adminpage"; // 기본 페이지 렌더링
    }




}
