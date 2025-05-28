package com.jpa.yanus.controller;


import com.jpa.yanus.entity.Attendance;
import com.jpa.yanus.entity.AttendanceTime;
import com.jpa.yanus.entity.Member;
import com.jpa.yanus.entity.NoWork;
import com.jpa.yanus.service.AttendanceService;
import com.jpa.yanus.service.AttendanceTimeService;
import com.jpa.yanus.service.MemberService;
import com.jpa.yanus.service.NoWorkService;
import com.jpa.yanus.type.StatusType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/*")
public class APIController {

    private final AttendanceService attendanceService;

    private final MemberService memberService;

    private final NoWorkService noWorkService;

    private final AttendanceTimeService attendanceTimeService;

    @PostMapping("checkIn")
    public ResponseEntity<String> checkIn(@SessionAttribute(name = "id", required = false) Long memberId, Attendance attendance){
        try {
            if (memberId == null) {
                // 세션 값이 없을 경우에 대한 처리
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Session not found");
            }

            Member member = memberService.getMemberById(memberId)
                    .orElseThrow(() -> new EntityNotFoundException("Member with id " + memberId + " not found"));

            LocalDateTime currentDateTime = LocalDateTime.now();
            log.info(currentDateTime.toString());
            attendance.setCheckInTime(currentDateTime);
            attendance.setCheckOutTime(currentDateTime);
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
            Optional<Attendance> mostRecentAttendance = attendanceService.findMostRecentAttendanceByMember(memberId);

            if (mostRecentAttendance == null) {
                // 회원의 출석 기록을 찾을 수 없는 경우
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("회원의 출석 기록을 찾을 수 없음");
            }

            log.info(mostRecentAttendance.toString());

            LocalDateTime currentDateTime = LocalDateTime.now();

            // 최근 출석 기록의 checkOutTime을 업데이트
            mostRecentAttendance.get().setCheckOutTime(currentDateTime);

            log.info(mostRecentAttendance.toString());
            attendanceService.getCheckOut(mostRecentAttendance.get());

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
    @PostMapping("NoWorkOk")
    public ResponseEntity<String> noWorkOk(@SessionAttribute(name = "id", required = false) Long memberId,
                                           @RequestBody List<String> noWorkList) {
        try {
            if (memberId == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("세션에서 회원을 찾을 수 없음");
            }

            // List<String>을 List<Long>으로 변환
            List<Long> convertedNoWorkList = noWorkList.stream()
                    .map(Long::valueOf)
                    .collect(Collectors.toList());

            noWorkService.noWorkOk(convertedNoWorkList);

            return ResponseEntity.status(HttpStatus.ACCEPTED).body("정상처리 되었습니다");

        } catch (NumberFormatException e) {
            // 변환 오류가 발생한 경우
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 형식의 데이터가 포함되어 있습니다");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("회원을 찾을 수 없음");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("오류 발생");
        }
    }
    @PostMapping("NoWorkNotOk")
    public ResponseEntity<String> noWorkNotOk(@SessionAttribute(name = "id", required = false) Long memberId,
                                           @RequestBody List<String> noWorkList) {
        try {
            if (memberId == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("세션에서 회원을 찾을 수 없음");
            }

            // List<String>을 List<Long>으로 변환
            List<Long> convertedNoWorkList = noWorkList.stream()
                    .map(Long::valueOf)
                    .collect(Collectors.toList());

            noWorkService.noWorkNotOk(convertedNoWorkList);

            return ResponseEntity.status(HttpStatus.ACCEPTED).body("정상처리 되었습니다");

        } catch (NumberFormatException e) {
            // 변환 오류가 발생한 경우
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 형식의 데이터가 포함되어 있습니다");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("회원을 찾을 수 없음");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("오류 발생");
        }
    }

    @PostMapping("insert-work-time")
    public ResponseEntity<String> insertWorkTime(@SessionAttribute(name = "id", required = false) Long memberId,
                                                 @RequestBody AttendanceTime attendanceTime) {
        try {
            if (memberId == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("세션이 종료되었습니다.");
            }
            log.info("???");
            // 회원 정보 조회
            Member member = memberService.getMemberById(memberId)
                    .orElseThrow(() -> new NoSuchElementException("해당 회원을 찾을 수 없습니다."));

            // 팀 번호 설정
            attendanceTime.setTeamNum(member.getMemberTeamNum());
            attendanceTime.setUploadDate(LocalDateTime.now());

            // 로직 처리 (추가 작업이 있다면 여기에 작성)
            attendanceTimeService.save(attendanceTime);

            return ResponseEntity.ok("근무 시간이 성공적으로 저장되었습니다.");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
        }
    }


}