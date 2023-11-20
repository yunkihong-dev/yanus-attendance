package com.jpa.yanus.repository;

import com.jpa.yanus.entity.Attendance;
import com.jpa.yanus.entity.Member;

import java.util.List;

public interface AttendanceQueryDSL {
    public List<Attendance> findAll();


}
