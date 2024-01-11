package com.jpa.yanus.service;

import com.jpa.yanus.domain.NoWorkDTO;
import com.jpa.yanus.domain.NoWorkWithOutMemberDTO;
import com.jpa.yanus.entity.NoWork;

import java.util.List;

public interface NoWorkService {

    public List<NoWorkDTO> getAllNoWorkWithMemberName();


    public void insertNoWork(NoWork noWork);

    public List<NoWorkWithOutMemberDTO> getMyAllNoWork(Long id);
}
