package com.jpa.yanus.service;

import com.jpa.yanus.entity.Member;

import java.util.Optional;

public interface MemberService {

    public Optional<Member> getMember(String memberId, String memberPassword);

    public Optional<Member> getMemberById(Long id);
}
