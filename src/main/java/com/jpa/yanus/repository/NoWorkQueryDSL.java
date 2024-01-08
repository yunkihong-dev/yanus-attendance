package com.jpa.yanus.repository;

import com.jpa.yanus.domain.NoWorkDTO;

import java.util.List;

public interface NoWorkQueryDSL {
    public List<NoWorkDTO> findAllWithMemberName();
}
