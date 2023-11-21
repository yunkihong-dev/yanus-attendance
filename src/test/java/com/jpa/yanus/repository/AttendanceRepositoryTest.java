package com.jpa.yanus.repository;


import com.jpa.yanus.entity.Attendance;
import com.jpa.yanus.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

@SpringBootTest @Slf4j @Rollback(false) @Transactional
public class AttendanceRepositoryTest {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private MemberRepository memberRepository;
    @Test
    public void findMostRecentAttendanceByMemberTest(){
        Member member = memberRepository.findById(1L).get();
        Attendance attendance =attendanceRepository.findMostRecentAttendanceByMember(member);
        log.info(attendance.toString());
    }
}
