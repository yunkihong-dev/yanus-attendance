package com.jpa.yanus.repository;

import com.jpa.yanus.domain.NoWorkDTO;
import com.jpa.yanus.domain.NoWorkWithOutMemberDTO;

import java.util.List;

public interface NoWorkQueryDSL {
    public List<NoWorkDTO> findAllWithMemberName();
    public List<NoWorkWithOutMemberDTO> findAllByMemberId(Long memberId);
}
