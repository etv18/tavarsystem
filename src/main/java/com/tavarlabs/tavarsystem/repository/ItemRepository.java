package com.tavarlabs.tavarsystem.repository;

import com.tavarlabs.tavarsystem.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
