package com.jpa.yanus.service;

import com.jpa.yanus.domain.NoWorkDTO;
import com.jpa.yanus.entity.NoWork;

import java.util.List;

public interface NoWorkService {

    public List<NoWork> getAll();


    public void insertNoWork(NoWorkDTO noWorkDTO);



    default NoWork toEntity(NoWorkDTO noWorkDTO){
        return NoWork.builder().id(noWorkDTO.getId())
                .category(noWorkDTO.getCategory())
                .detail(noWorkDTO.getDetail())
                .selectedDate(noWorkDTO.getSelectedDate())
                .uploadDate(noWorkDTO.getUploadDate())
                .member(noWorkDTO.getMember())
                .build();
    }
}
