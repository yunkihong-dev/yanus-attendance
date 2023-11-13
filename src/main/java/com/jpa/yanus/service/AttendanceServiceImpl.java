package com.jpa.yanus.service;

import com.jpa.yanus.entity.Attendance;
import com.jpa.yanus.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AttendanceServiceImpl implements AttendanceService{

    @Autowired
    AttendanceRepository attendanceRepository;
    @Override
    public List<Attendance> getAll() {
        return attendanceRepository.findAll();
    }
}
