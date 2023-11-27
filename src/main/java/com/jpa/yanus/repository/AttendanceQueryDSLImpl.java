package com.jpa.yanus.repository;

import com.jpa.yanus.entity.Attendance;
import com.jpa.yanus.entity.Member;
import com.querydsl.core.NonUniqueResultException;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.jpa.yanus.entity.QAttendance.attendance;

@Slf4j
@Repository

public class AttendanceQueryDSLImpl implements AttendanceQueryDSL{


    @Autowired
    private JPAQueryFactory query;

    @Override
    public List<Attendance> findAll() {
        final List<Attendance> attendances = query.select(attendance).from(attendance).fetch();
        return attendances;
    }

    @Override
    public Attendance findMostRecentAttendanceByMember(Long memberId) {
        final Attendance foundAttendance = query.selectFrom(attendance)
                .where(attendance.member.id.eq(memberId))
                .orderBy(attendance.checkInTime.desc())
                .fetchFirst();

        return foundAttendance;
    }

}
