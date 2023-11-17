package com.jpa.yanus.service;

import com.jpa.yanus.entity.Attendance;

import java.util.List;


public interface AttendanceService {

    public List<Attendance> getAll();
    public void checkIn(Long memberId) ;

    public void checkOut(Long memberId);
}
