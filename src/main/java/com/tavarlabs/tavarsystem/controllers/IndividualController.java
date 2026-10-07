package com.tavarlabs.tavarsystem.controllers;

import com.tavarlabs.tavarsystem.dtos.individual.CreateIndividualRequestDto;
import com.tavarlabs.tavarsystem.dtos.individual.IndividualDto;
import com.tavarlabs.tavarsystem.entity.Individual;
import com.tavarlabs.tavarsystem.mappers.IndividualMapper;
import com.tavarlabs.tavarsystem.service.IndividualService;
import com.tavarlabs.tavarsystem.utils.AppKeywords;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = AppKeywords.apiUrlPfx + "/individual")
public class IndividualController {
    private final IndividualService individualService;
    private final IndividualMapper individualMapper;

    @PostMapping("/create")
    public ResponseEntity<?> createIndividual(
            @RequestBody CreateIndividualRequestDto requestDto
    ) {
        Individual individual =  individualService.createIndividual(requestDto);
        return ResponseEntity.ok(individualMapper.toResponseDto(individual));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll(){
        List<Individual> individuals = individualService.getAllIndividuals();
        List<IndividualDto> individualsDto = individuals.stream()
                .map(indvd -> {
                    return individualMapper.toResponseDto(indvd);
                })
                .toList();
        return ResponseEntity.ok(individualsDto);
    }
}
