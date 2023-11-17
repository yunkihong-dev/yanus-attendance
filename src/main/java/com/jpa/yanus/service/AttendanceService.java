package com.jpa.yanus.service;

import com.jpa.yanus.entity.Attendance;
import com.jpa.yanus.entity.Member;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

public interface AttendanceService {

    public List<Attendance> getAll();
    public void checkIn(Long memberId) ;

    public void checkOut(Long memberId);
}
