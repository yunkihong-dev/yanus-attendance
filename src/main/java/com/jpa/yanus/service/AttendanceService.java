package com.jpa.yanus.service;

import com.jpa.yanus.domain.AttendanceDTO;
import com.jpa.yanus.domain.MemberDTO;
import com.jpa.yanus.entity.Attendance;
import com.jpa.yanus.entity.Member;

import java.util.List;


public interface AttendanceService {

    public List<Attendance> getAll();


    default Attendance toEntity(AttendanceDTO attendanceDTO){
        return Attendance.builder().id(attendanceDTO.getId())
                .checkInTime(attendanceDTO.getCheckInTime())
                .checkOutTime(attendanceDTO.getCheckOutTime())
                .member(attendanceDTO.getMember())
                .build();
    }
}
