package com.tavarlabs.tavarsystem.repository;

import com.tavarlabs.tavarsystem.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query("SELECT i FROM Item i WHERE i.deleted = false AND i.publicId = :publicId")
    Optional<Item> findUndeletedByPublicId(UUID publicId);

    @Query("SELECT i FROM Item i WHERE i.deleted = false AND i.code = :code")
    Optional<Item> findUndeletedByCode(String code);

    @Query("SELECT i FROM Item i WHERE i.deleted = false")
    List<Item> findAllUndeleted();
}
