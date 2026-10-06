package com.tavarlabs.tavarsystem.service.impl;

import com.tavarlabs.tavarsystem.dtos.individual.DtoCreateIndividualRequest;
import com.tavarlabs.tavarsystem.entity.Individual;
import com.tavarlabs.tavarsystem.repository.IndividualRepository;
import com.tavarlabs.tavarsystem.service.IndividualService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IndividualServiceImpl implements IndividualService {
    private final IndividualRepository individualRepo;

    @Override
    public Individual createIndividual(DtoCreateIndividualRequest dto) {
        Individual individual = Individual.builder()
                .firstName(dto.getFirstName().toUpperCase())
                .lastName(dto.getLastName().toUpperCase())
                .documentType(dto.getDocumentType())
                .documentNumber(dto.getDocumentNumber().toUpperCase())
                .email(dto.getEmail().toUpperCase())
                .telephone(dto.getTelephone().toUpperCase())
                .address(dto.getAddress().toUpperCase())
                .build();
        return individualRepo.save(individual);
    }

    @Override
    public List<Individual> getAllIndividuals() {
        return individualRepo.findAll();
    }
}
