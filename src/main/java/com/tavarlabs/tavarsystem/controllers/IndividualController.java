package com.tavarlabs.tavarsystem.controllers;

import com.tavarlabs.tavarsystem.dtos.individual.CreateIndividualRequestDto;
import com.tavarlabs.tavarsystem.dtos.individual.IndividualDto;
import com.tavarlabs.tavarsystem.dtos.individual.UpdateIndividualRequest;
import com.tavarlabs.tavarsystem.dtos.message.SimpleMessage;
import com.tavarlabs.tavarsystem.entity.Individual;
import com.tavarlabs.tavarsystem.mappers.IndividualMapper;
import com.tavarlabs.tavarsystem.service.IndividualService;
import com.tavarlabs.tavarsystem.utils.AppKeywords;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = AppKeywords.apiUrlPfx + "/individual")
public class IndividualController {
    private final IndividualService individualService;
    private final IndividualMapper individualMapper;

    @PostMapping
    public IndividualDto createIndividual(
            @RequestBody CreateIndividualRequestDto requestDto
    ) {
        return individualService.createIndividual(requestDto);
    }

    @GetMapping
    public List<IndividualDto> getAll(){
        return individualService.getAllIndividuals();
    }

    @PutMapping
    public IndividualDto updateIndividual(
            @RequestBody UpdateIndividualRequest requestDto
    ) {
        Individual individual =  individualService.updateIndividual(requestDto);
        return individualMapper.toResponseDto(individual);
    }

    @GetMapping("/{publicId}")
    public IndividualDto getSingleIndividual(
            @PathVariable("publicId") UUID publicId
    ){
        Individual individual = individualService.getSingleIndividual(publicId);
        return individualMapper.toResponseDto(individual);
    }


    @DeleteMapping("/{publicId}")
    public SimpleMessage deleteIndividual(
            @PathVariable("publicId") UUID publicId
    ){
        individualService.deleteIndividual(publicId);
        return new SimpleMessage("Individual deleted successfully.");
    }
}
