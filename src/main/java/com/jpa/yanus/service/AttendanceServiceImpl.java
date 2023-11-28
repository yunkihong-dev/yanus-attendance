package com.jpa.yanus.service;

import com.jpa.yanus.domain.AttendanceDTO;
import com.jpa.yanus.entity.Attendance;
import com.jpa.yanus.repository.AttendanceRepository;
import com.jpa.yanus.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional @Slf4j
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    AttendanceRepository attendanceRepository;

    @Autowired
    MemberRepository memberRepository;

    @Override
    public List<Attendance> getAll() {
        return attendanceRepository.findAll();
    }

    @Override
    public void getCheckIn(AttendanceDTO attendanceDTO) {
        attendanceRepository.save(toEntity(attendanceDTO));
    }

    @Override
    public void getCheckOut(AttendanceDTO attendanceDTO) {
        attendanceRepository.save(toEntity(attendanceDTO));
    }

    @Override
    public Attendance findMostRecentAttendanceByMember(Long memberId) {
        return attendanceRepository.findMostRecentAttendanceByMember(memberId);
    }


}
