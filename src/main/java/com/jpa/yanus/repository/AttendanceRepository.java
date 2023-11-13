package com.jpa.yanus.repository;

import com.jpa.yanus.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<Attendance, Long>, AttendanceQueryDSL{
}