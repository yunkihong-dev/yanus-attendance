package com.jpa.yanus.service;

import com.jpa.yanus.entity.Attendance;
import com.jpa.yanus.entity.Member;
import com.jpa.yanus.repository.AttendanceRepository;
import com.jpa.yanus.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional @Slf4j
public class AttendanceServiceImpl implements AttendanceService{

    @Autowired
    AttendanceRepository attendanceRepository;

    @Autowired
    MemberRepository memberRepository;

    @Override
    public List<Attendance> getAll() {
        return attendanceRepository.findAll();
    }

    @Override
    public void checkIn(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("ID가 " + memberId + "인 회원을 찾을 수 없습니다."));

        Attendance attendance = new Attendance();
        attendance.setCheckInTime(LocalDateTime.now());
        attendance.setMember(member);

        attendanceRepository.save(attendance);
    }

    public void checkOut(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("ID가 " + memberId + "인 회원을 찾을 수 없습니다."));

        Attendance attendance = attendanceRepository.findTopByMemberOrderByIdDesc(member);

        if (attendance == null || attendance.getCheckOutTime() != null) {
            // 오류 처리 또는 예외 처리, 예를 들어 이미 체크아웃된 회원
        }

        attendance.setCheckOutTime(LocalDateTime.now());
        attendanceRepository.save(attendance);
    }
}
