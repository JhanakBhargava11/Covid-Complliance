package com.xebia.covidComplianceApplication.repository;

import com.xebia.covidComplianceApplication.dto.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
}
