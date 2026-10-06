package com.tavarlabs.tavarsystem.controllers;

import com.tavarlabs.tavarsystem.dtos.individual.DtoCreateIndividualRequest;
import com.tavarlabs.tavarsystem.entity.Individual;
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

    @PostMapping("/create")
    public ResponseEntity<?> createIndividual(
            @RequestBody DtoCreateIndividualRequest requestDto
    ) {
        Individual individual =  individualService.createIndividual(requestDto);
        return ResponseEntity.ok(individual);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll(){
        List<Individual> individuals = individualService.getAllIndividuals();
        return ResponseEntity.ok(individuals);
    }
}
