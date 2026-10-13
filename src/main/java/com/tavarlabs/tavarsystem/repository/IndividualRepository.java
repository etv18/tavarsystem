package com.tavarlabs.tavarsystem.repository;

import com.tavarlabs.tavarsystem.entity.Individual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IndividualRepository extends JpaRepository<Individual, Long> {
    @Query("SELECT i FROM Individual i WHERE i.publicId = :publicId AND i.deleted = false")
    Optional<Individual> findUndeletedByPublicId(@Param("publicId") UUID publicId);

    @Query("SELECT i FROM Individual i WHERE i.deleted = false")
    List<Individual> findUndeletedAll();
}
