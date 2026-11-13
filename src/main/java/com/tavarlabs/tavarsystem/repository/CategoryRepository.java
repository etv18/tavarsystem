package com.tavarlabs.tavarsystem.repository;

import com.tavarlabs.tavarsystem.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
