package com.jpa.yanus.controller;


import com.jpa.yanus.entity.Attendance;
import com.jpa.yanus.entity.Member;
import com.jpa.yanus.entity.NoWork;
import com.jpa.yanus.service.AttendanceService;
import com.jpa.yanus.service.MemberService;
import com.jpa.yanus.service.NoWorkService;
import com.jpa.yanus.type.StatusType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/*")
public class APIController {

    @Autowired
    private final AttendanceService attendanceService;

    @Autowired
    private final MemberService memberService;

    @Autowired
    private final NoWorkService noWorkService;

    @PostMapping("checkIn")
    public ResponseEntity<String> checkIn(@SessionAttribute(name = "id", required = false) Long memberId, Attendance attendance){
        try {
            if (memberId == null) {
                // 세션 값이 없을 경우에 대한 처리
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Session not found");
            }

            Member member = memberService.getMemberById(memberId)
                    .orElseThrow(() -> new EntityNotFoundException("Member with id " + memberId + " not found"));

            ZonedDateTime currentKST = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));



            attendance.setCheckInTime(currentKST);
            attendance.setCheckOutTime(currentKST);
            attendance.setMember(member);

            attendanceService.getCheckIn(attendance);

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



    @PostMapping("checkOut")
    public ResponseEntity<String> checkOut(@SessionAttribute(name = "id", required = false) Long memberId) {
        try {
            if (memberId == null) {
                // memberId가 세션에 없는 경우, 404 응답 반환
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("세션에서 회원을 찾을 수 없음");
            }
            // 회원의 최근 출석 기록을 찾음
            Attendance mostRecentAttendance = attendanceService.findMostRecentAttendanceByMember(memberId);

            if (mostRecentAttendance == null) {
                // 회원의 출석 기록을 찾을 수 없는 경우
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("회원의 출석 기록을 찾을 수 없음");
            }

            log.info(mostRecentAttendance.toString());

            ZonedDateTime currentDateTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));

            // 최근 출석 기록의 checkOutTime을 업데이트
            mostRecentAttendance.setCheckOutTime(currentDateTime);

            log.info(mostRecentAttendance.toString());
            // attendanceService.getCheckOut 메서드가 AttendanceDTO를 기대하므로, toDTO 메서드를 사용하여 변환
            attendanceService.getCheckOut(mostRecentAttendance);

            // 성공 응답 반환
            return ResponseEntity.ok("체크아웃 성공");
        } catch (EntityNotFoundException e) {
            // 회원을 찾을 수 없는 경우
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("회원을 찾을 수 없음");
        } catch (Exception e) {
            // 기타 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("체크아웃 중 오류 발생");
        }
    }

    @PostMapping("noWork")
    public ResponseEntity<String> sendNoWork(@SessionAttribute(name = "id", required = false) Long memberId, @RequestBody NoWork noWork) {
        try {
            if (memberId == null) {
                // memberId가 세션에 없는 경우, 404 응답 반환
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("세션에서 회원을 찾을 수 없음");
            }
            Member member = memberService.getMemberById(memberId)
                    .orElseThrow(() -> new EntityNotFoundException("Member with id " + memberId + " not found"));

            LocalDate localDate = LocalDate.now();
            noWork.setMember(member);
            noWork.setUploadDate(localDate);
            noWork.setStatus(StatusType.WAITING);

            // noWorkDTO에 이미 selectedDate가 있으므로 따로 파싱할 필요 없음

            noWorkService.insertNoWork(noWork);

            // 성공 응답 반환
            return ResponseEntity.ok("체크아웃 성공");
        } catch (EntityNotFoundException e) {
            // 회원을 찾을 수 없는 경우
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("회원을 찾을 수 없음");
        } catch (Exception e) {
            // 기타 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("오류 발생");
        }
    }



}