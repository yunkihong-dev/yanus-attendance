package com.jpa.yanus.service;

import com.jpa.yanus.domain.MemberDTO;
import com.jpa.yanus.entity.Member;
import com.jpa.yanus.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional @Slf4j
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;


    @Override
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    @Override
    public Optional<Member> getMember(String memberId, String memberPassword) {
        return memberRepository.findByMemberIdAndMemberPassword(memberId,memberPassword);
    }

    @Override
    public Optional<Member> getMemberById(Long id) {
        return memberRepository.findById(id);
    }

    @Override
    public void insertMember(MemberDTO memberDTO) {
        memberRepository.save(toEntity(memberDTO));
    }
}
