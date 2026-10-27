package com.tavarlabs.tavarsystem.repository;

import com.tavarlabs.tavarsystem.entity.Action;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface ActionRepository extends JpaRepository<Action, Long> {
    List<Action> findAllByPublicIdIn(Collection<UUID> publicIds);
    List<Action> findAllByCodeIn(Collection<String> codes);
    boolean existsByCode(String code);
    @Query("select a.code from Action a order by a.code")
    List<String> findAllCodes();
}
