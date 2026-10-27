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
import java.util.Map;

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

    @PutMapping("/update")
    public ResponseEntity<?> updateIndividual(
            @RequestBody IndividualDto requestDto
    ) {
        Individual individual =  individualService.updateIndividual(requestDto);
        return ResponseEntity.ok(individualMapper.toResponseDto(individual));
    }

    @GetMapping("/{publicId}")
    public ResponseEntity<?> getSingleIndividual(@PathVariable("publicId") String publicId){
        Individual individual = individualService.getSingleIndividual(publicId);
        return ResponseEntity.ok(individualMapper.toResponseDto(individual));
    }


    @DeleteMapping("/{publicId}")
    public ResponseEntity<?> deleteIndividual(@PathVariable("publicId") String publicId){
        individualService.deleteIndividual(publicId);
        return ResponseEntity.ok(Map.of("message", "Individual deleted successfully."));
    }
}
