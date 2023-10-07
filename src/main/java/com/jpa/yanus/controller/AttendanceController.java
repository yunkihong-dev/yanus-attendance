package com.jpa.yanus.controller;

import com.jpa.yanus.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/attendance/*")
public class AttendanceController {
    private final MemberService memberService;


//    String clientIp = request.getHeader("X-Forwarded-For"); // 프록시 서버를 통한 접근 시 고려
//            if (clientIp == null || clientIp.isEmpty() || "unknown".equalsIgnoreCase(clientIp)) {
//        clientIp = request.getHeader("Proxy-Client-IP");
//    }
//            if (clientIp == null || clientIp.isEmpty() || "unknown".equalsIgnoreCase(clientIp)) {
//        clientIp = request.getHeader("WL-Proxy-Client-IP");
//    }
//            if (clientIp == null || clientIp.isEmpty() || "unknown".equalsIgnoreCase(clientIp)) {
//        clientIp = request.getHeader("HTTP_X_FORWARDED_FOR");
//    }
//            if (clientIp == null || clientIp.isEmpty() || "unknown".equalsIgnoreCase(clientIp)) {
//        clientIp = request.getRemoteAddr(); // IPv6 주소일 수 있음
//    }
//            log.info(clientIp);


}