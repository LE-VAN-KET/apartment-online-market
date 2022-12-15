package com.cdcn.apartmentonlinemarket.users.services.impl;

import com.cdcn.apartmentonlinemarket.exception.StageNotFoundException;
import com.cdcn.apartmentonlinemarket.users.domain.dto.StageDto;
import com.cdcn.apartmentonlinemarket.users.domain.entity.Stages;
import com.cdcn.apartmentonlinemarket.users.domain.mapper.StageMapper;
import com.cdcn.apartmentonlinemarket.users.repository.StageRepository;
import com.cdcn.apartmentonlinemarket.users.services.StageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StageServiceImpl implements StageService {
    private final StageRepository stageRepository;
    private final StageMapper stageMapper;


    @Override
    public List<StageDto> getAllStages() {
        List<Stages> stagesList = stageRepository.findAll();
        return stagesList.isEmpty() ? Collections.emptyList() : stageMapper.convertToDtoList(stagesList);
    }

    @Override
    public StageDto getOne(Long id) {
        Stages stage = stageRepository.findOne(id).orElseThrow(() -> new StageNotFoundException(404, "Stage not found!"));
        return stageMapper.convertToDto(stage);
    }
}
