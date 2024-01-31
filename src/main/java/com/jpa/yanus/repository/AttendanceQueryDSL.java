package com.jpa.yanus.repository;

import com.jpa.yanus.domain.AttendanceForWeekDTO;
import com.jpa.yanus.domain.AttendanceMemberJoinDTO;
import com.jpa.yanus.entity.Attendance;

import java.util.List;
import java.util.Optional;

public interface AttendanceQueryDSL {
    public List<Attendance> findAll();

    public Optional<Attendance> findMostRecentAttendanceByMember(Long memberId);

    public List<AttendanceMemberJoinDTO> findMostResentAttendanceByTeamNum(int memberTeamNum);
    public List<AttendanceMemberJoinDTO> findAllMostResentAttendance();
    public List<AttendanceForWeekDTO> findWeekAttendanceById(Long memberId);
}
