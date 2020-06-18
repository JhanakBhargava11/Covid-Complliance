package com.xebia.innovationportal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xebia.innovationportal.entities.IdeaStatus;

@Repository
public interface IdeaStatusRespository extends JpaRepository<IdeaStatus, Integer> {

	public IdeaStatus findByStatus(String status);
}
