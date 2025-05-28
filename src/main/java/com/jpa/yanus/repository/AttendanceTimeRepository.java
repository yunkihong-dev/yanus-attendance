package com.jpa.yanus.repository;

import com.jpa.yanus.entity.AttendanceTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceTimeRepository  extends JpaRepository<AttendanceTime, Long>, AttendanceTimeQueryDSL {
}
