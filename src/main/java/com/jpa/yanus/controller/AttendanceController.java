package com.jpa.yanus.controller;

import com.jpa.yanus.entity.Attendance;
import com.jpa.yanus.service.MemberService;
import com.sun.xml.messaging.saaj.packaging.mime.Header;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpRequest;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/attendance/*")
public class AttendanceController {

    @GetMapping("getIp")
    @ResponseBody
    public String getIp() throws Exception {
        String ip = null;
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        // IPv4 주소를 얻기 위한 헤더들을 확인합니다. IPv6 주소를 무시합니다.
        String[] headersToCheck = {"X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP",
                "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR", "X-Real-IP",
                "X-RealIP", "REMOTE_ADDR"};

        for (String header : headersToCheck) {
            ip = request.getHeader(header);
            if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
                // 첫 번째로 찾은 유효한 IPv4 주소를 반환합니다.
                log.info(ip);
                return ip;
            }
        }

        // 모든 헤더에서 유효한 IPv4 주소를 찾지 못한 경우, 기본적으로 사용자의 IP 주소를 반환합니다.
        ip = request.getRemoteAddr();
        log.info(ip);
        return ip;

    }

}