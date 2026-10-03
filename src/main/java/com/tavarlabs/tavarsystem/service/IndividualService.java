package com.tavarlabs.tavarsystem.service;

import com.tavarlabs.tavarsystem.dtos.individual.DtoCreateIndividualRequest;
import com.tavarlabs.tavarsystem.entity.Individual;

import java.util.Optional;

public interface IndividualService {
    Optional<Individual> createIndividual(DtoCreateIndividualRequest individualRequest);
}
