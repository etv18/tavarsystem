package com.tavarlabs.tavarsystem.repository;

import com.tavarlabs.tavarsystem.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByPublicId(UUID publicId);
}
