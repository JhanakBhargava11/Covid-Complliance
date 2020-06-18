package com.xebia.innovationportal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xebia.innovationportal.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
