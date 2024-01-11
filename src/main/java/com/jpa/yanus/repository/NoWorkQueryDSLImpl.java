package com.jpa.yanus.repository;

import com.jpa.yanus.domain.NoWorkDTO;
import com.jpa.yanus.domain.NoWorkWithOutMemberDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import static com.jpa.yanus.entity.QNoWork.noWork;
import static com.jpa.yanus.entity.QMember.member;

@Slf4j
@RequiredArgsConstructor
public class NoWorkQueryDSLImpl implements NoWorkQueryDSL {

    private final JPAQueryFactory query;


    @Override
    public List<NoWorkDTO> findAllWithMemberName() {
        return query.select(Projections.constructor(NoWorkDTO.class,
                noWork.id,
                noWork.category,
                noWork.detail,
                member.memberName,
                noWork.selectedDate,
                noWork.uploadDate,
                noWork.status
                ))
                .from(noWork)
                .join(noWork.member, member)
                .orderBy(noWork.status.asc(),noWork.uploadDate.asc())
                .fetch();
    }

    @Override
    public List<NoWorkWithOutMemberDTO> findAllByMemberId(Long memberId) {
        return query.select(Projections.constructor(NoWorkWithOutMemberDTO.class,
                noWork.id,
                noWork.category,
                noWork.detail,
                noWork.selectedDate,
                noWork.uploadDate,
                noWork.status))
                .from(noWork)
                .where(noWork.member.id.eq(memberId))
                .fetch();
    }


}
