package com.tavarlabs.tavarsystem.repository;

import com.tavarlabs.tavarsystem.entity.Action;
import com.tavarlabs.tavarsystem.entity.UserAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserActionRepository extends JpaRepository<UserAction, Long> {
    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM UserAction ua WHERE ua.user.id = :userId")
    Long deleteAllByUserId(@Param("userId") Long userId);
}
