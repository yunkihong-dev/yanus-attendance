package com.jpa.yanus.repository;

import com.jpa.yanus.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository  extends JpaRepository<Member, Long> , MemberQueryDSL{
}
