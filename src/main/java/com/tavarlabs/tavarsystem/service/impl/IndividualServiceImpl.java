package com.tavarlabs.tavarsystem.service.impl;

import com.tavarlabs.tavarsystem.dtos.individual.CreateIndividualRequestDto;
import com.tavarlabs.tavarsystem.dtos.individual.IndividualDto;
import com.tavarlabs.tavarsystem.entity.Individual;
import com.tavarlabs.tavarsystem.entity.User;
import com.tavarlabs.tavarsystem.enums.DocumentType;
import com.tavarlabs.tavarsystem.mappers.IndividualMapper;
import com.tavarlabs.tavarsystem.repository.IndividualRepository;
import com.tavarlabs.tavarsystem.service.IndividualService;
import com.tavarlabs.tavarsystem.utils.AppFunctions;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IndividualServiceImpl implements IndividualService {
    private final IndividualRepository individualRepo;
    private final IndividualMapper individualMapper;

    @Override
    public IndividualDto createIndividual(CreateIndividualRequestDto dto) {
        if(individualRepo.existsByEmailIgnoreCase(dto.getEmail())) {
            throw new DataIntegrityViolationException("This email is already in used. Email = "+dto.getEmail());
        }

        if(individualRepo.existsByDocumentNumberAndDocumentType(dto.getDocumentNumber(), dto.getDocumentType())) {
            throw new DataIntegrityViolationException("This document number is already in used. # = "+dto.getDocumentNumber());
        }

        Individual individual = Individual.builder()
                .firstName(upper(dto.getFirstName()))
                .lastName(upper(dto.getLastName()))
                .documentType(dto.getDocumentType())
                .documentNumber(upper(dto.getDocumentNumber()))
                .email(upper(dto.getEmail()))
                .telephone(dto.getTelephone())
                .address(upper(dto.getAddress()))
                .build();

        return individualMapper.toResponseDto(individualRepo.save(individual));
    }

    @Override
    public List<IndividualDto> getAllIndividuals() {
        List<Individual> individuals = individualRepo.findUndeletedAll();
        return individuals.stream()
                .map(individualMapper::toResponseDto)
                .toList();
    }

    @Transactional
    @Override
    public Individual updateIndividual(IndividualDto dto) {
        if(dto == null) {
            throw new IllegalArgumentException("You must provide accurate info about the record you want to update.");
        }

        Individual savedIndi = getSingleIndividual(dto.getPublicId());

        if(differentStrings(savedIndi.getFirstName(), dto.getFirstName())){
            savedIndi.setFirstName(upper(dto.getFirstName()));
        }

        if(differentStrings(savedIndi.getLastName(), dto.getLastName())){
            savedIndi.setLastName(upper(dto.getLastName()));
        }

        // TODO: ADD DocumentType VALIDATION BEFORE UPDATING IT
        if(savedIndi.getDocumentType() != dto.getDocumentType() && dto.getDocumentType() != null){
            savedIndi.setDocumentType(dto.getDocumentType());
        }

        if(differentStrings(savedIndi.getDocumentNumber(), dto.getDocumentNumber()) && dto.getDocumentNumber() != null){
            if(individualRepo.existsByDocumentNumberAndDocumentType(dto.getDocumentNumber(), dto.getDocumentType())) {
                throw new DataIntegrityViolationException("This document number is already in used. doc-number = "+dto.getDocumentNumber());
            }
            savedIndi.setDocumentNumber(upper(dto.getDocumentNumber()));
        }

        // TODO: ADD EMAIL VALIDATION BEFORE UPDATING IT
        if(differentStrings(savedIndi.getEmail(), dto.getEmail())){
            if(individualRepo.existsByEmailIgnoreCase(dto.getEmail()) && dto.getEmail() != null) {
                throw new DataIntegrityViolationException("This email is already in used. Email = "+dto.getEmail());
            }
            savedIndi.setEmail(AppFunctions.upper(dto.getEmail()));
        }

        if(differentStrings(savedIndi.getTelephone(), dto.getTelephone())){
            savedIndi.setTelephone(dto.getTelephone());
        }

        if(differentStrings(savedIndi.getAddress(), dto.getAddress())){
            savedIndi.setAddress(upper(dto.getAddress()));
        }

        return individualRepo.save(savedIndi);
    }

    @Override
    public Individual getSingleIndividual(String publicId) {
        return individualRepo.findUndeletedByPublicId(UUID.fromString(publicId))
                .orElseThrow(() -> new EntityNotFoundException(
                        "Individual was not found. id = " + publicId
                ));
    }

    @Override
    public Individual getSingleIndividual(String publicId, String messageWhenNotFound) {
        return individualRepo.findUndeletedByPublicId(UUID.fromString(publicId))
                .orElseThrow(() -> new EntityNotFoundException(messageWhenNotFound));
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

    private String upper(String arg){
        return AppFunctions.upper(arg);
    }
}
