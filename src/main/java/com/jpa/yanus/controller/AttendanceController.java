package com.jpa.yanus.controller;

import com.jpa.yanus.repository.MemberRepository;
import com.jpa.yanus.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/attendance/*")
public class AttendanceController {

    private final AttendanceService attendanceService;
    private final MemberRepository memberRepository;

}