package com.tavarlabs.tavarsystem.service;

import com.tavarlabs.tavarsystem.dtos.individual.DtoCreateIndividualRequest;
import com.tavarlabs.tavarsystem.entity.Individual;

public interface IndividualService {
    Individual createIndividual(DtoCreateIndividualRequest individualRequest);
}
