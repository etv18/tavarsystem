package com.tavarlabs.tavarsystem.repository;

import com.tavarlabs.tavarsystem.entity.Individual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IndividualRepository extends JpaRepository<Individual, UUID> {
}
