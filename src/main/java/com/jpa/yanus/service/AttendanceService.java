package com.jpa.yanus.service;

import com.jpa.yanus.domain.AttendanceDTO;
import com.jpa.yanus.entity.Attendance;

import java.util.List;


public interface  AttendanceService {

    public List<Attendance> getAll();

    public void getCheckIn(AttendanceDTO attendanceDTO);

    public void getCheckOut(AttendanceDTO attendanceDTO);

    public Attendance findMostRecentAttendanceByMember(Long memberId);

    default Attendance toEntity(AttendanceDTO attendanceDTO){
        return Attendance.builder().id(attendanceDTO.getId())
                .checkInTime(attendanceDTO.getCheckInTime())
                .checkOutTime(attendanceDTO.getCheckOutTime())
                .member(attendanceDTO.getMember())
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
