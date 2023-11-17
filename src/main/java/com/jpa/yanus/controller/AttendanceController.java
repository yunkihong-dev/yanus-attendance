package com.jpa.yanus.controller;

import com.jpa.yanus.domain.AttendanceDTO;
import com.jpa.yanus.entity.Member;
import com.jpa.yanus.repository.MemberRepository;
import com.jpa.yanus.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/attendance/*")
public class AttendanceController {

    private final AttendanceService attendanceService;
    private final MemberRepository memberRepository;
    @PostMapping("/checkIn")
    public void checkIn(@SessionAttribute(name = "id", required = false) Long memberId, AttendanceDTO attendanceDTO) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member with id " + memberId + " not found"));
        LocalDateTime currentDateTime = LocalDateTime.now();

        attendanceDTO.setCheckInTime(currentDateTime);
        attendanceDTO.setCheckOutTime(currentDateTime);
        attendanceDTO.setMember(member);

        attendanceService.checkIn(attendanceDTO);
    }

    @GetMapping("/checkOut")
    public  void checkOut(@SessionAttribute(name = "id", required = false) Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member with id " + memberId + " not found"));
        LocalDateTime currentDateTime = LocalDateTime.now();

        attendanceService.checkOut(memberId);
    }
}