package com.tavarlabs.tavarsystem.repository;

import com.tavarlabs.tavarsystem.entity.Individual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IndividualRepository extends JpaRepository<Individual, UUID> {
    @Query("SELECT i FROM Individual i WHERE i.id = :publicId AND i.deleted = false")
    Optional<Individual> findUndeletedByPublicId(@Param("publicId") UUID publicId);
}
