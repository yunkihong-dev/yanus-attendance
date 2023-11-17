package com.jpa.yanus.repository;

import com.jpa.yanus.entity.Attendance;
import com.jpa.yanus.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<Attendance, Long>, AttendanceQueryDSL{
    Attendance findTopByMemberOrderByIdDesc(Member member);
}