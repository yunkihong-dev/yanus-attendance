package com.jpa.yanus.entityTest;

import com.jpa.yanus.domain.MemberDTO;
import com.jpa.yanus.entity.Member;
import com.jpa.yanus.repository.MemberRepository;
import com.jpa.yanus.service.MemberService;
import com.jpa.yanus.type.MemberType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

@SpringBootTest @Slf4j @Rollback(false) @Transactional
public class MemberTest {
    @Autowired
    private MemberService memberService;

    @Test
    public void getMemberServiceTest(){
        log.info(memberService.getMember("ricky0130","yunki123").toString());
    }



    @Test
    public void inputUser(){
        MemberDTO memberdto = new MemberDTO();
        memberdto.setMemberId("ricky0130");
        memberdto.setMemberName("홍윤기");
        memberdto.setMemberPassword("yunki123");
        memberdto.setMemberType(MemberType.NORMAL);
        memberService.insertMember(memberdto);
    }

}
