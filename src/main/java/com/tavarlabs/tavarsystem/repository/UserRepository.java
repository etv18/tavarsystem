package com.tavarlabs.tavarsystem.repository;

import com.tavarlabs.tavarsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    // Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.username = :username AND u.deleted = false")
    Optional<User> findUndeletedByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.id = :publicId AND u.deleted = false")
    Optional<User> findUndeletedByPublicId(@Param("publicId") UUID publicId);
}
