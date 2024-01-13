package com.jpa.yanus.service;

import com.jpa.yanus.domain.NoWorkDTO;
import com.jpa.yanus.domain.NoWorkWithOutMemberDTO;
import com.jpa.yanus.entity.NoWork;
import com.jpa.yanus.repository.NoWorkRepository;
import com.jpa.yanus.type.StatusType;
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
    public List<NoWorkDTO> getAllNoWorkWithMemberName() {
        return noWorkRepository.findAllWithMemberName();
    }

    @Override
    public void insertNoWork(NoWork noWork) {
        noWorkRepository.save(noWork);
    }

    @Override
    public List<NoWorkWithOutMemberDTO> getMyAllNoWork(Long id) {
        return noWorkRepository.findAllByMemberId(id);
    }

    @Override
    public void noWorkOk(List<Long> noWorkList) {
        List<NoWork> noWorkLists= noWorkRepository.findAllById(noWorkList);

        for(NoWork noWork : noWorkLists){
            noWork.setStatus(StatusType.ABLE);
        }

        noWorkRepository.saveAll(noWorkLists);
    }

    @Override
    public void noWorkNotOk(List<Long> noWorkList) {
        List<NoWork> noWorkLists= noWorkRepository.findAllById(noWorkList);

        for(NoWork noWork : noWorkLists){
            noWork.setStatus(StatusType.UNABLE);
        }

        noWorkRepository.saveAll(noWorkLists);
    }

}
