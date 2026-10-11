package com.tavarlabs.tavarsystem.repository;

import com.tavarlabs.tavarsystem.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TokenRepository extends JpaRepository<Token, UUID> {
}
