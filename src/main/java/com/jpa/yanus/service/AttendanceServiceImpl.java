package com.jpa.yanus.service;

import com.jpa.yanus.domain.AttendanceDTO;
import com.jpa.yanus.domain.AttendanceMemberJoinDTO;
import com.jpa.yanus.entity.Attendance;
import com.jpa.yanus.repository.AttendanceRepository;
import com.jpa.yanus.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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
    public void getCheckIn(Attendance attendance) {

        log.info(attendance.getCheckInTime().toString()+"서비스");
        attendanceRepository.save(attendance);
    }

    @Override
    public void getCheckOut(Attendance attendance) {
        attendanceRepository.save(attendance);
    }

    @Override
    public Optional<Attendance> findMostRecentAttendanceByMember(Long memberId) {
        return attendanceRepository.findMostRecentAttendanceByMember(memberId);
    }

    @Override
    public List<AttendanceMemberJoinDTO> findMyTeamAttendanceToday(int memberTeamNum) {
        log.info(attendanceRepository.findMostResentAttendanceByTeamNum(memberTeamNum)+"서비스");
        return attendanceRepository.findMostResentAttendanceByTeamNum(memberTeamNum);
    }

    @Override
    public List<AttendanceMemberJoinDTO> findAllAttendanceToday() {
        return attendanceRepository.findAllMostResentAttendance();
    }


}
