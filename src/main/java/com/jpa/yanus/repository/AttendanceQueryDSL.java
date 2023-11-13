package com.jpa.yanus.repository;

import com.jpa.yanus.entity.Attendance;

import java.util.List;

public interface AttendanceQueryDSL {
    public List<Attendance> findAll();

}
