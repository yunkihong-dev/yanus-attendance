package com.jpa.yanus.service;

import com.jpa.yanus.domain.NoWorkDTO;
import com.jpa.yanus.entity.NoWork;
import com.jpa.yanus.repository.NoWorkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class NoWorkServiceImpl implements NoWorkService {

    private final NoWorkRepository noWorkRepository;


    @Override
    public List<NoWork> getAll() {
        return noWorkRepository.findAll();
    }

    @Override
    public void insertNoWork(NoWorkDTO noWorkDTO) {
        noWorkRepository.save(toEntity(noWorkDTO));
    }

}
