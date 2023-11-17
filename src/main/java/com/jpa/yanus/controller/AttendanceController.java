package com.jpa.yanus.controller;

import com.jpa.yanus.domain.AttendanceDTO;
import com.jpa.yanus.entity.Member;
import com.jpa.yanus.repository.MemberRepository;
import com.jpa.yanus.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/checkIn")
    public ResponseEntity<String> checkIn(@SessionAttribute(name = "id", required = false) Long memberId, AttendanceDTO attendanceDTO) {
        try {
            Member member = memberRepository.findById(memberId)
                    .orElseThrow(() -> new EntityNotFoundException("Member with id " + memberId + " not found"));

            LocalDateTime currentDateTime = LocalDateTime.now();

            attendanceDTO.setCheckInTime(currentDateTime);
            attendanceDTO.setCheckOutTime(currentDateTime);
            attendanceDTO.setMember(member);

            attendanceService.checkIn(attendanceDTO);

            // Return a success response
            return ResponseEntity.ok("Check-in successful");
        } catch (EntityNotFoundException e) {
            // Member not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Member not found");
        } catch (Exception e) {
            // Other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during check-in");
        }
    }


    @GetMapping("/checkOut")
    public  void checkOut(@SessionAttribute(name = "id", required = false) Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member with id " + memberId + " not found"));
        LocalDateTime currentDateTime = LocalDateTime.now();

        attendanceService.checkOut(memberId);
    }
}