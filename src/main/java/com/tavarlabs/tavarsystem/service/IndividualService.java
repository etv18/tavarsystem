package com.tavarlabs.tavarsystem.service;

import com.tavarlabs.tavarsystem.dtos.individual.CreateIndividualRequestDto;
import com.tavarlabs.tavarsystem.dtos.individual.IndividualDto;
import com.tavarlabs.tavarsystem.dtos.individual.UpdateIndividualRequest;
import com.tavarlabs.tavarsystem.entity.Individual;

import java.util.List;
import java.util.UUID;

public interface IndividualService {
    IndividualDto createIndividual(CreateIndividualRequestDto dto);
    List<IndividualDto> getAllIndividuals();
    Individual updateIndividual(UpdateIndividualRequest dto);
    Individual getSingleIndividual(String publicId);
    Individual getSingleIndividual(String publicId, String messageWhenNotFound);
    void deleteIndividual(String publicId);
}
