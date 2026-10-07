package com.tavarlabs.tavarsystem.service;

import com.tavarlabs.tavarsystem.dtos.individual.CreateIndividualRequestDto;
import com.tavarlabs.tavarsystem.dtos.individual.IndividualDto;
import com.tavarlabs.tavarsystem.entity.Individual;

import java.util.List;

public interface IndividualService {
    Individual createIndividual(CreateIndividualRequestDto dto);
    List<Individual> getAllIndividuals();
    Individual updateIndividual(IndividualDto dto);
}
