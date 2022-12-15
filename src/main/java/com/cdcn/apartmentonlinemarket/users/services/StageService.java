package com.cdcn.apartmentonlinemarket.users.services;

import com.cdcn.apartmentonlinemarket.users.domain.dto.StageDto;

import java.util.List;

public interface StageService {
    List<StageDto> getAllStages();
    StageDto getOne(Long id);
}
