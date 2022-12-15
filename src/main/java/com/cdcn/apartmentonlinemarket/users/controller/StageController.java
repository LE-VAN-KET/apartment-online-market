package com.cdcn.apartmentonlinemarket.users.controller;

import com.cdcn.apartmentonlinemarket.users.domain.dto.StageDto;
import com.cdcn.apartmentonlinemarket.users.services.StageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/stages")
public class StageController {
    private StageService stageService;

    @Autowired
    public StageController(StageService stageService) {
        this.stageService = stageService;
    }

    @GetMapping
    public List<StageDto> getAllStage() {
        return stageService.getAllStages();
    }

    @GetMapping("{id}")
    public StageDto getOneStage(@PathVariable("id") Long id) {
        return stageService.getOne(id);
    }
}
