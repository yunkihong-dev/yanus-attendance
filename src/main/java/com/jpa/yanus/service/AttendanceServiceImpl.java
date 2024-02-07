package com.jpa.yanus.service;

import com.jpa.yanus.domain.AttendanceDTO;
import com.jpa.yanus.domain.AttendanceMemberJoinDTO;
import com.jpa.yanus.entity.Attendance;
import com.jpa.yanus.repository.AttendanceRepository;
import com.jpa.yanus.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Scheduled(cron = "0 0 0 * * *")
    public void getCheckoutScheduled() {
        List<AttendanceMemberJoinDTO> attendanceListDTO= attendanceRepository.findAllMostResentAttendance();
        log.info(attendanceListDTO.toString());
        log.info("자동 퇴근하러옴");
        for (AttendanceMemberJoinDTO dto : attendanceListDTO) {
            Attendance attendance = toEntity(dto, memberRepository);
            log.info("퇴근을 누르지 않은자는 "+attendance.getMember().getMemberName());
            if(attendance.getCheckInTime().equals(attendance.getCheckOutTime())){
                LocalDateTime now = LocalDateTime.now();
                attendance.setCheckOutTime(now);
                attendanceRepository.save(attendance);
            }
        }
    }

}
