package com.tavarlabs.tavarsystem.repository;

import com.tavarlabs.tavarsystem.entity.TaxType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaxTypeRepository extends JpaRepository<TaxType, Long> {
    TaxType findByName(String name);
}
