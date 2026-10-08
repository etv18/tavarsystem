package com.tavarlabs.tavarsystem.service.impl;

import com.tavarlabs.tavarsystem.dtos.individual.CreateIndividualRequestDto;
import com.tavarlabs.tavarsystem.dtos.individual.IndividualDto;
import com.tavarlabs.tavarsystem.entity.Individual;
import com.tavarlabs.tavarsystem.repository.IndividualRepository;
import com.tavarlabs.tavarsystem.service.IndividualService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IndividualServiceImpl implements IndividualService {
    private final IndividualRepository individualRepo;

    @Override
    public Individual createIndividual(CreateIndividualRequestDto dto) {
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

    @Override
    public Individual updateIndividual(IndividualDto dto) {
        if(dto == null) {
            throw new IllegalArgumentException("You must provide full info about the user you want to update.");
        }

        Individual currentIndi = individualRepo.findById(UUID.fromString(dto.getId()))
                .orElseThrow(() -> new EntityNotFoundException(
                        "User with this id was not found: " + dto.getId()
                ));

        if(differentStrings(currentIndi.getFirstName(), dto.getFirstName())){
            currentIndi.setFirstName(dto.getFirstName().toUpperCase());
        }

        if(differentStrings(currentIndi.getLastName(), dto.getLastName())){
            currentIndi.setLastName(dto.getLastName().toUpperCase());
        }

        // TODO: ADD DocumentType VALIDATION BEFORE UPDATING IT
        if(differentStrings(currentIndi.getDocumentType().toString(), dto.getDocumentType().toString())){
            currentIndi.setDocumentType(dto.getDocumentType());
        }

        if(differentStrings(currentIndi.getDocumentNumber(), dto.getDocumentNumber())){
            currentIndi.setDocumentNumber(dto.getDocumentNumber().toUpperCase());
        }

        // TODO: ADD EMAIL VALIDATION BEFORE UPDATING IT
        if(differentStrings(currentIndi.getEmail(), dto.getEmail())){
            currentIndi.setEmail(dto.getEmail().toUpperCase());
        }

        if(differentStrings(currentIndi.getTelephone(), dto.getTelephone())){
            currentIndi.setTelephone(dto.getTelephone().toUpperCase());
        }

        if(differentStrings(currentIndi.getAddress(), dto.getAddress())){
            currentIndi.setAddress(dto.getAddress().toUpperCase());
        }

        // TODO: ADD statement for updating user obj

        return individualRepo.save(currentIndi);
    }

    private boolean differentStrings(String currentVal, String newVal){
        if(currentVal == null || newVal == null) return true;
        return !currentVal.equalsIgnoreCase(newVal);
    }
}
