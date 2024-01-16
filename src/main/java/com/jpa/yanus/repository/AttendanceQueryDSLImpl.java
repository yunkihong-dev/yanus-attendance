package com.jpa.yanus.repository;

import com.jpa.yanus.domain.AttendanceForWeekDTO;
import com.jpa.yanus.domain.AttendanceMemberJoinDTO;
import com.jpa.yanus.entity.Attendance;
import com.jpa.yanus.entity.QAttendance;
import com.jpa.yanus.entity.QMember;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.jpa.yanus.entity.QAttendance.attendance;
import static com.querydsl.core.types.Projections.*;


@Slf4j
@Repository
@RequiredArgsConstructor
public class AttendanceQueryDSLImpl implements AttendanceQueryDSL {


    private final JPAQueryFactory query;

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
        LocalDate today = LocalDate.now();
        LocalDateTime startOfToday = today.atStartOfDay();
        LocalDateTime endOfToday = today.plusDays(1).atStartOfDay();

        QAttendance attendance = QAttendance.attendance;
        QAttendance subAttendance = new QAttendance("subAttendance");
        QMember member = QMember.member;

        // 각 회원별 최초 출근 시간 찾기
        JPQLQuery<LocalDateTime> subQuery = JPAExpressions
                .select(subAttendance.checkInTime.min())
                .from(subAttendance)
                .where(subAttendance.member.id.eq(member.id)
                        .and(subAttendance.checkInTime.between(startOfToday, endOfToday)));

        // 서브쿼리를 사용하여 각 회원별 최초 출근 기록만 가져오기
                List<AttendanceMemberJoinDTO> result = query
                .select(Projections.constructor(AttendanceMemberJoinDTO.class,
                        attendance.id,
                        attendance.checkInTime,
                        attendance.checkOutTime,
                        member.memberName,
                        member.memberTeamNum))
                .from(attendance)
                .join(attendance.member, member)
                .where(member.memberTeamNum.eq(memberTeamNum)
                        .and(attendance.checkInTime.eq(subQuery)))
                .orderBy(attendance.checkInTime.asc())
                .fetch();
        log.info(result.toString()+"QueryDSL");
        return result;
    }

    @Override
    public List<AttendanceMemberJoinDTO> findAllMostResentAttendance() {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfToday = today.atStartOfDay();
        LocalDateTime endOfToday = today.plusDays(1).atStartOfDay();

        QAttendance attendance = QAttendance.attendance;
        QMember member = QMember.member;

        // QueryDSL을 사용하여 쿼리를 작성합니다.
        List<AttendanceMemberJoinDTO> result = query
                .select(Projections.constructor(AttendanceMemberJoinDTO.class,
                        attendance.id,
                        attendance.checkInTime,
                        attendance.checkOutTime,
                        member.memberName,
                        member.memberTeamNum))
                .from(attendance)
                .join(attendance.member, member)
                .where(attendance.checkInTime.between(startOfToday, endOfToday))
                .orderBy(attendance.checkInTime.asc())
                .fetch();
        log.info(result.toString() + "QueryDSL");
        return result;
    }

    @Override
    public List<AttendanceForWeekDTO> findWeekAttendanceById(Long memberId) {

        QAttendance attendance = QAttendance.attendance;

        LocalDateTime startDate = LocalDateTime.now().with(DayOfWeek.MONDAY).withHour(0).withMinute(0).withSecond(0).withNano(0);
        log.info(startDate.toString());

        LocalDateTime endDate = LocalDateTime.now().with(DayOfWeek.SUNDAY).withHour(23).withMinute(59).withSecond(59).withNano(999999999);
        log.info(endDate.toString());
        String sums = "SUM(CASE WHEN {0}.checkInTime >= {1} AND {0}.checkOutTime <= {2} AND FUNCTION('DAYOFWEEK', {0}.checkInTime) = {3} THEN TIMESTAMPDIFF(SECOND, {0}.checkInTime, {0}.checkOutTime) ELSE 0 END)";
        List<AttendanceForWeekDTO> result = query
                .select(Projections.bean(AttendanceForWeekDTO.class,
                        Expressions.template(Double.class,
                                "SUM(CASE WHEN {0}.checkInTime >= {1} AND {0}.checkOutTime <= {2} AND FUNCTION('DAYOFWEEK', {0}.checkInTime) = {3} THEN TIMESTAMPDIFF(SECOND, {0}.checkInTime, {0}.checkOutTime) ELSE 0 END)",
                                attendance, startDate, endDate, 2).as("Mon"),
                        Expressions.template(Double.class,
                                "SUM(CASE WHEN {0}.checkInTime >= {1} AND {0}.checkOutTime <= {2} AND FUNCTION('DAYOFWEEK', {0}.checkInTime) = {3} THEN TIMESTAMPDIFF(SECOND, {0}.checkInTime, {0}.checkOutTime) ELSE 0 END)",
                                attendance, startDate, endDate, 3).as("Tue"),
                        Expressions.template(Double.class,
                                "SUM(CASE WHEN {0}.checkInTime >= {1} AND {0}.checkOutTime <= {2} AND FUNCTION('DAYOFWEEK', {0}.checkInTime) = {3} THEN TIMESTAMPDIFF(SECOND, {0}.checkInTime, {0}.checkOutTime) ELSE 0 END)",
                                attendance, startDate, endDate, 4).as("Wed"),
                        Expressions.template(Double.class,
                                "SUM(CASE WHEN {0}.checkInTime >= {1} AND {0}.checkOutTime <= {2} AND FUNCTION('DAYOFWEEK', {0}.checkInTime) = {3} THEN TIMESTAMPDIFF(SECOND, {0}.checkInTime, {0}.checkOutTime) ELSE 0 END)",
                                attendance, startDate, endDate, 5).as("Thur"),
                        Expressions.template(Double.class,
                                "SUM(CASE WHEN {0}.checkInTime >= {1} AND {0}.checkOutTime <= {2} AND FUNCTION('DAYOFWEEK', {0}.checkInTime) = {3} THEN TIMESTAMPDIFF(SECOND, {0}.checkInTime, {0}.checkOutTime) ELSE 0 END)",
                                attendance, startDate, endDate, 6).as("Fri"),
                        Expressions.template(Double.class,
                                "SUM(CASE WHEN {0}.checkInTime >= {1} AND {0}.checkOutTime <= {2} AND FUNCTION('DAYOFWEEK', {0}.checkInTime) = {3} THEN TIMESTAMPDIFF(SECOND, {0}.checkInTime, {0}.checkOutTime) ELSE 0 END)",
                                attendance, startDate, endDate, 7).as("Sat"),
                        Expressions.template(Double.class,
                                "SUM(CASE WHEN {0}.checkInTime >= {1} AND {0}.checkOutTime <= {2} AND FUNCTION('DAYOFWEEK', {0}.checkInTime) = {3} THEN TIMESTAMPDIFF(SECOND, {0}.checkInTime, {0}.checkOutTime) ELSE 0 END)",
                                attendance, startDate, endDate, 1).as("Sun")))
                .from(attendance)
                .where(attendance.member.id.eq(memberId)
                        .and(attendance.checkInTime.between(startDate, endDate)))
                .fetch();
        return result;
    }


}
