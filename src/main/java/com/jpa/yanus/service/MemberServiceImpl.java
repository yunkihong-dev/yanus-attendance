package com.jpa.yanus.service;

import com.jpa.yanus.domain.MemberDTO;
import com.jpa.yanus.domain.MemberTeamUpdateDTO;
import com.jpa.yanus.entity.Member;
import com.jpa.yanus.repository.MemberRepository;
import com.jpa.yanus.type.StatusType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;


    @Override
    @Transactional(readOnly = true)
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Member> getMember(String memberId, String memberPassword) {
        return memberRepository.findByMemberIdAndMemberPassword(memberId,memberPassword);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Member> getMemberById(Long id) {
        return memberRepository.findById(id);
    }

    @Override
    @Transactional
    public void insertMember(MemberDTO memberDTO) {
        memberRepository.save(toEntity(memberDTO));
    }

    @Override
    @Transactional
    public List<Member> softDeleteByMemberIds(List<Long> ids) {
        List<Member> members = memberRepository.findAllById(ids);

        for (Member member : members) {
            member.setMemberStatus(StatusType.UNABLE);
        }

        return members;
    }

    @Override
    @Transactional
    public void changeTeam(List<MemberTeamUpdateDTO> memberTeamUpdateDTOList) {
        for(MemberTeamUpdateDTO memberTeamUpdateDTO : memberTeamUpdateDTOList) {
            Optional<Member> member = memberRepository.findById(memberTeamUpdateDTO.getMemberId());
            member.get().setMemberTeamNum(memberTeamUpdateDTO.getTeamNum());
        }
    }

}
