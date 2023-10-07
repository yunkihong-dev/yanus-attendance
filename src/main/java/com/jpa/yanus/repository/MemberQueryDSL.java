package com.jpa.yanus.repository;

import com.jpa.yanus.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberQueryDSL {
    //    상품목록
    public List<Member> findAll();

    public Optional<Member> findByMemberIdAndMemberPassword(String memberId, String memberPassword);
}
