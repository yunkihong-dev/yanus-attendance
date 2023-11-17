package com.jpa.yanus.controller;

import com.jpa.yanus.domain.AttendanceDTO;
import com.jpa.yanus.entity.Attendance;
import com.jpa.yanus.entity.Member;
import com.jpa.yanus.service.AttendanceService;
import com.jpa.yanus.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/attendance/*")
public class AttendanceController {

    private final AttendanceService attendanceService;
    private final MemberService memberService;

    @PostMapping("/checkIn")
    public void checkIn(@RequestBody HttpSession session) {
        attendanceService.checkIn((Long)session.getAttribute("id"));
    }

    @GetMapping("/checkOut")
    public  void checkOut(@RequestBody HttpSession session) {
        attendanceService.checkOut((Long)session.getAttribute("id"));
    }
}