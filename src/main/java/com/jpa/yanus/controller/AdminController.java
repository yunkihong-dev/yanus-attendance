package com.jpa.yanus.controller;

import com.jpa.yanus.service.AttendanceService;
import com.jpa.yanus.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public void goAdmin(Model model){

    }


}
