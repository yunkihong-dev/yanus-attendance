package com.jpa.yanus.repository;

import com.jpa.yanus.entity.NoWork;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoWorkRepository extends JpaRepository<NoWork, Long>, NoWorkQueryDSL {
}
