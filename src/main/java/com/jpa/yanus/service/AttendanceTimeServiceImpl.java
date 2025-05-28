package com.jpa.yanus.service;

import com.jpa.yanus.entity.AttendanceTime;
import com.jpa.yanus.repository.AttendanceTimeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AttendanceTimeServiceImpl implements AttendanceTimeService {

    @Autowired
    private AttendanceTimeRepository attendanceTimeRepository;

    @Override
    public AttendanceTime save(AttendanceTime attendanceTime) {
        return attendanceTimeRepository.save(attendanceTime);
    }
}
