package com.jpa.yanus.service;

import com.jpa.yanus.domain.MemberDTO;
import com.jpa.yanus.entity.Member;

import java.util.Optional;

public interface MemberService {

    public Optional<Member> getMember(String memberId, String memberPassword);

    public Optional<Member> getMemberById(Long id);

    public void insertMember(MemberDTO memberDTO);

    default Member toEntity(MemberDTO memberDTO){
        return Member.builder().id(memberDTO.getId())
                .memberName(memberDTO.getMemberName())
                .memberPassword(memberDTO.getMemberPassword())
                .memberName(memberDTO.getMemberName())
                .memberType(memberDTO.getMemberType())
                .build();
    }

}
