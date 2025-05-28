package com.jpa.yanus.repository;


import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class AttendanceTimeQueryDSLImpl implements AttendanceTimeQueryDSL{

    private final JPAQueryFactory query;


}
