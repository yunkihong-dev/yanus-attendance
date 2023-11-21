package com.jpa.yanus.repository;

import com.jpa.yanus.entity.Attendance;
import com.jpa.yanus.entity.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.jpa.yanus.entity.QAttendance.attendance;

public class AttendanceQueryDSLImpl implements AttendanceQueryDSL{


    @Autowired
    private JPAQueryFactory query;

    @Override
    public List<Attendance> findAll() {
        final List<Attendance> attendances = query.select(attendance).from(attendance).fetch();
        return attendances;
    }

    @Override
    public Attendance findMostRecentAttendanceByMember(Member member) {
        return query.selectFrom(attendance)
                .where(attendance.member.eq(member))
                .orderBy(attendance.checkInTime.desc())
                .fetchFirst();
    }

}
