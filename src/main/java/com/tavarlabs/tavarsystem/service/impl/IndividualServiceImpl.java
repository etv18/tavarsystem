package com.tavarlabs.tavarsystem.service.impl;

import com.tavarlabs.tavarsystem.dtos.individual.CreateIndividualRequestDto;
import com.tavarlabs.tavarsystem.dtos.individual.IndividualDto;
import com.tavarlabs.tavarsystem.entity.Individual;
import com.tavarlabs.tavarsystem.repository.IndividualRepository;
import com.tavarlabs.tavarsystem.service.IndividualService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    @Override
    public Individual updateIndividual(IndividualDto dto) {
        if(dto == null) {
            throw new IllegalArgumentException("You must provide accurate info about the record you want to update.");
        }

        Individual savedIndi = getSingleIndividual(dto.getId());

        if(differentStrings(savedIndi.getFirstName(), dto.getFirstName())){
            savedIndi.setFirstName(dto.getFirstName().toUpperCase());
        }

        if(differentStrings(savedIndi.getLastName(), dto.getLastName())){
            savedIndi.setLastName(dto.getLastName().toUpperCase());
        }

        // TODO: ADD DocumentType VALIDATION BEFORE UPDATING IT
        if(differentStrings(savedIndi.getDocumentType().name(), dto.getDocumentType().name())){
            savedIndi.setDocumentType(dto.getDocumentType());
        }

        if(differentStrings(savedIndi.getDocumentNumber(), dto.getDocumentNumber())){
            savedIndi.setDocumentNumber(dto.getDocumentNumber().toUpperCase());
        }

        // TODO: ADD EMAIL VALIDATION BEFORE UPDATING IT
        if(differentStrings(savedIndi.getEmail(), dto.getEmail())){
            savedIndi.setEmail(dto.getEmail().toUpperCase());
        }

        if(differentStrings(savedIndi.getTelephone(), dto.getTelephone())){
            savedIndi.setTelephone(dto.getTelephone().toUpperCase());
        }

        if(differentStrings(savedIndi.getAddress(), dto.getAddress())){
            savedIndi.setAddress(dto.getAddress().toUpperCase());
        }

        // TODO: ADD statement for updating user obj

        return individualRepo.save(savedIndi);
    }

    @Override
    public Individual getSingleIndividual(String publicId) {
        return individualRepo.findUndeletedByPublicId(UUID.fromString(publicId))
                .orElseThrow(() -> new EntityNotFoundException(
                        "Individual was not found. id = " + publicId
                ));
    }

    @Transactional
    @Override
    public void deleteIndividual(String publicId) {
        Individual individual = getSingleIndividual(publicId);
        individual.setDeleted(true);
        individualRepo.save(individual);
    }

    private boolean differentStrings(String currentVal, String newVal){
        if(currentVal == null || newVal == null) return true;
        return !currentVal.equalsIgnoreCase(newVal);
    }
}
