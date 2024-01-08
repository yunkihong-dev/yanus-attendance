package com.jpa.yanus.repository;

import com.jpa.yanus.domain.NoWorkDTO;
import com.jpa.yanus.entity.QMember;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import static com.jpa.yanus.entity.QNoWork.noWork;

@Slf4j
public class NoWorkQueryDSLImpl implements NoWorkQueryDSL {
    @Autowired
    private JPAQueryFactory query;


    @Override
    public List<NoWorkDTO> findAllWithMemberName() {
        return query.select(Projections.constructor(NoWorkDTO.class,
                noWork.id,
                noWork.category,
                noWork.detail,
                QMember.member.memberName,
                noWork.selectedDate,
                noWork.uploadDate,
                noWork.status
                ))
                .from(noWork)
                .join(noWork.member, QMember.member)
                .orderBy(noWork.status.asc(),noWork.uploadDate.asc())
                .fetch();
    }
}
