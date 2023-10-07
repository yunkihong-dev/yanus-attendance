package com.jpa.yanus.repository;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

@SpringBootTest
@Slf4j
@Transactional
@Rollback(false)
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;


    @Test
    public void findByMemberIdAndPasswordTest(){
        log.info(memberRepository.findByMemberIdAndMemberPassword("ricky0130","yunki123").toString());
    }


}
