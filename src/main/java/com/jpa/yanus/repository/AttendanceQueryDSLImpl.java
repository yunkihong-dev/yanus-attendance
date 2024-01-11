package com.jpa.yanus.repository;

import com.jpa.yanus.domain.AttendanceMemberJoinDTO;
import com.jpa.yanus.entity.Attendance;
import com.jpa.yanus.entity.QAttendance;
import com.jpa.yanus.entity.QMember;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.*;
import java.util.List;

import static com.jpa.yanus.entity.QAttendance.attendance;

@Slf4j
@Repository

public class AttendanceQueryDSLImpl implements AttendanceQueryDSL {


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

    @Override
    public List<AttendanceMemberJoinDTO> findMostResentAttendanceByTeamNum(int memberTeamNum) {
        ZoneId zoneId = ZoneId.of("Asia/Seoul"); // 한국 시간대 설정
        ZonedDateTime today = ZonedDateTime.now(zoneId);
        ZonedDateTime startOfToday = ZonedDateTime.now(zoneId).toLocalDate().atStartOfDay(zoneId);
        ZonedDateTime endOfToday = startOfToday.plusDays(1);


        QAttendance attendance = QAttendance.attendance;
        QAttendance subAttendance = new QAttendance("subAttendance");
        QMember member = QMember.member;

        // 각 회원별 최초 출근 시간 찾기

        JPQLQuery<ZonedDateTime> subQuery = JPAExpressions
                .select(subAttendance.checkInTime.min())
                .from(subAttendance)
                .where(subAttendance.member.id.eq(member.id)
                        .and(subAttendance.checkInTime.goe(startOfToday))
                        .and(subAttendance.checkInTime.loe(endOfToday)));
        // 서브쿼리를 사용하여 각 회원별 최초 출근 기록만 가져오기
        return query
                .select(Projections.constructor(AttendanceMemberJoinDTO.class,
                        attendance.id,
                        attendance.checkInTime,
                        attendance.checkOutTime,
                        member.memberName,
                        member.memberTeamNum))
                .from(attendance)
                .join(attendance.member, member)
                .where(member.memberTeamNum.eq(memberTeamNum)
                        .and(attendance.checkInTime.in(subQuery)))
                .orderBy(attendance.checkInTime.asc())
                .fetch();
    }


    @Override
    public List<AttendanceMemberJoinDTO> findAllMostResentAttendance() {
        ZoneId zoneId = ZoneId.of("Asia/Seoul"); // 한국 시간대 설정
        ZonedDateTime startOfToday = ZonedDateTime.now(zoneId).toLocalDate().atStartOfDay(zoneId);
        ZonedDateTime endOfToday = startOfToday.plusDays(1);

        QAttendance attendance = QAttendance.attendance;
        QAttendance subAttendance = new QAttendance("subAttendance");
        QMember member = QMember.member;

        // 각 회원별 최초 출근 시간 찾기


        JPQLQuery<ZonedDateTime> subQuery = JPAExpressions
                .select(subAttendance.checkInTime.min())
                .from(subAttendance)
                .where(subAttendance.member.id.eq(member.id)
                        .and(subAttendance.checkInTime.goe(startOfToday))
                        .and(subAttendance.checkInTime.loe(endOfToday)));

        // 서브쿼리를 사용하여 각 회원별 최초 출근 기록만 가져오기
        return query
                .select(Projections.constructor(AttendanceMemberJoinDTO.class,
                        attendance.id,
                        attendance.checkInTime,
                        attendance.checkOutTime,
                        member.memberName,
                        member.memberTeamNum))
                .from(attendance)
                .join(attendance.member, member)
                .where(attendance.checkInTime.in(subQuery))
                .orderBy(attendance.checkInTime.asc())
                .fetch();
    }


}
