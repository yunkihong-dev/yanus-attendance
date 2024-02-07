package com.jpa.yanus.service;

import com.jpa.yanus.domain.AttendanceDTO;
import com.jpa.yanus.domain.AttendanceMemberJoinDTO;
import com.jpa.yanus.entity.Attendance;
import com.jpa.yanus.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;


public interface  AttendanceService {

    public List<Attendance> getAll();

    public void getCheckIn(Attendance attendance);

    public void getCheckOut(Attendance attendance);

    public Optional<Attendance> findMostRecentAttendanceByMember(Long memberId);

    public List<AttendanceMemberJoinDTO> findMyTeamAttendanceToday(int memberTeamNum);

    public List<AttendanceMemberJoinDTO> findAllAttendanceToday();




    default Attendance toEntity(AttendanceDTO attendanceDTO){
        return Attendance.builder().id(attendanceDTO.getId())
                .checkInTime(attendanceDTO.getCheckInTime())
                .checkOutTime(attendanceDTO.getCheckOutTime())
                .member(attendanceDTO.getMember())
                .build();
    }

    default Attendance toEntity(AttendanceMemberJoinDTO attendanceMemberJoinDTODTO, MemberRepository memberRepository){
        return Attendance.builder().id(attendanceMemberJoinDTODTO.getId())
                .checkInTime(attendanceMemberJoinDTODTO.getCheckInTime())
                .checkOutTime(attendanceMemberJoinDTODTO.getCheckOutTime())
                .member(memberRepository.findByMemberName(attendanceMemberJoinDTODTO.getMemberName()))
                .build();
    }
    default AttendanceDTO toDTO(Attendance attendance){
        return AttendanceDTO.builder().id(attendance.getId())
                .checkInTime(attendance.getCheckInTime())
                .checkOutTime(attendance.getCheckOutTime())
                .member(attendance.getMember())
                .build();
    }
}
