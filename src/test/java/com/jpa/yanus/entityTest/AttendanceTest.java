package com.jpa.yanus.entityTest;

import com.jpa.yanus.repository.AttendanceRepository;
import com.jpa.yanus.service.AttendanceService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

@SpringBootTest
@Slf4j
@Rollback(false) @Transactional
public class AttendanceTest {
    @Autowired
    private AttendanceRepository attendanceRepository;

    @Test
    public void findWeekAttendanceById(){
        log.info(attendanceRepository.findWeekAttendanceById(2L).toString());
    }
}
