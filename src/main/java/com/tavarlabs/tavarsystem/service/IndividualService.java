package com.tavarlabs.tavarsystem.service;

import com.tavarlabs.tavarsystem.dtos.individual.CreateIndividualRequestDto;
import com.tavarlabs.tavarsystem.dtos.individual.IndividualDto;
import com.tavarlabs.tavarsystem.entity.Individual;

import java.util.List;
import java.util.UUID;

public interface IndividualService {
    Individual createIndividual(CreateIndividualRequestDto dto);
    List<Individual> getAllIndividuals();
    Individual updateIndividual(IndividualDto dto);
    Individual getSingleIndividual(String publicId);
}
