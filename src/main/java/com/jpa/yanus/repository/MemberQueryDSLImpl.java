package com.jpa.yanus.repository;

import com.jpa.yanus.entity.Member;
import static com.jpa.yanus.entity.QMember.member;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Slf4j
public class MemberQueryDSLImpl implements MemberQueryDSL{

    @Autowired
    private JPAQueryFactory query;


    @Override
    public List<Member> findAll() {

        final List<Member> members = query.select(member).from(member).fetch();
        return members;
    }

    @Override
    public Optional<Member> findByMemberIdAndMemberPassword(String memberId, String memberPassword) {
        Member foundMember = query.selectFrom(member)
                .where(member.memberId.eq(memberId)
                        .and(member.memberPassword.eq(memberPassword)))
                .fetchOne();
        return Optional.ofNullable(foundMember);
    }
}
