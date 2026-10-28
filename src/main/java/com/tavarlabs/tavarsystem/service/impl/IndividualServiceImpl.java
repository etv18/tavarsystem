package com.tavarlabs.tavarsystem.service.impl;

import com.tavarlabs.tavarsystem.dtos.individual.CreateIndividualRequestDto;
import com.tavarlabs.tavarsystem.dtos.individual.IndividualDto;
import com.tavarlabs.tavarsystem.dtos.individual.UpdateIndividualRequest;
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
        if(individualRepo.existsByEmailIgnoreCase(dto.email())) {
            throw new DataIntegrityViolationException("This email is already in used. Email = "+dto.email());
        }

        if(individualRepo.existsByDocumentNumberAndDocumentType(dto.documentNumber(), dto.documentType())) {
            throw new DataIntegrityViolationException("This document number is already in used. # = "+dto.documentNumber());
        }

        Individual individual = Individual.builder()
                .firstName(upper(dto.firstName()))
                .lastName(upper(dto.lastName()))
                .documentType(dto.documentType())
                .documentNumber(upper(dto.documentNumber()))
                .email(upper(dto.email()))
                .telephone(dto.telephone())
                .address(upper(dto.address()))
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
    public Individual updateIndividual(UpdateIndividualRequest dto) {
        if (dto == null) {
            throw new IllegalArgumentException("You must provide accurate info about the record you want to update.");
        }

        Individual savedIndi = getSingleIndividual(dto.publicId());

        if (differentStrings(savedIndi.getFirstName(), dto.firstName())) {
            savedIndi.setFirstName(upper(dto.firstName()));
        }

        if (differentStrings(savedIndi.getLastName(), dto.lastName())) {
            savedIndi.setLastName(upper(dto.lastName()));
        }

        // TODO: ADD DocumentType VALIDATION BEFORE UPDATING IT
        if (dto.documentType() != null && savedIndi.getDocumentType() != dto.documentType()) {
            if(individualRepo.existsByDocumentNumberAndDocumentType(savedIndi.getDocumentNumber(), dto.documentType())) {
                throw new DataIntegrityViolationException("This document number is already in used. # = "+dto.documentNumber());
            }
            savedIndi.setDocumentType(dto.documentType());
        }

        if (dto.documentNumber() != null && differentStrings(savedIndi.getDocumentNumber(), dto.documentNumber())) {
            if (individualRepo.existsByDocumentNumberAndDocumentType(dto.documentNumber(), dto.documentType())) {
                throw new DataIntegrityViolationException("This document number is already in used. doc-number = " + dto.documentNumber());
            }
            savedIndi.setDocumentNumber(upper(dto.documentNumber()));
        }

        // TODO: ADD EMAIL VALIDATION BEFORE UPDATING IT
        if (differentStrings(savedIndi.getEmail(), dto.email())) {
            if (dto.email() != null && individualRepo.existsByEmailIgnoreCase(dto.email())) {
                throw new DataIntegrityViolationException("This email is already in used. Email = " + dto.email());
            }
            savedIndi.setEmail(upper(dto.email()));
        }

        if (differentStrings(savedIndi.getTelephone(), dto.telephone())) {
            savedIndi.setTelephone(dto.telephone());
        }

        if (differentStrings(savedIndi.getAddress(), dto.address())) {
            savedIndi.setAddress(upper(dto.address()));
        }

        return individualRepo.save(savedIndi);
    }

    @Override
    public Individual getSingleIndividual(UUID publicId) {
        return individualRepo.findUndeletedByPublicId(publicId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Individual was not found. id = " + publicId
                ));
    }

    @Override
    public Individual getSingleIndividual(UUID publicId, String messageWhenNotFound) {
        return individualRepo.findUndeletedByPublicId(publicId)
                .orElseThrow(() -> new EntityNotFoundException(messageWhenNotFound));
    }

    @Transactional
    @Override
    public void deleteIndividual(UUID publicId) {
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
