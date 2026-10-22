package com.tavarlabs.tavarsystem.repository;

import com.tavarlabs.tavarsystem.entity.Action;
import com.tavarlabs.tavarsystem.entity.UserAction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserActionRepository extends JpaRepository<UserAction, Long> {

}
