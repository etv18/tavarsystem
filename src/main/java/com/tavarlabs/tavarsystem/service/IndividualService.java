package com.tavarlabs.tavarsystem.service;

import com.tavarlabs.tavarsystem.dtos.individual.DtoCreateIndividualRequest;
import com.tavarlabs.tavarsystem.entity.Individual;

import java.util.List;

public interface IndividualService {
    Individual createIndividual(DtoCreateIndividualRequest dto);
    List<Individual> getAllIndividuals();
}
