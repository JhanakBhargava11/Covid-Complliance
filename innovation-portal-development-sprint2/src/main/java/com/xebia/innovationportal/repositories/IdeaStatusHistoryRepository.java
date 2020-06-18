package com.xebia.innovationportal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xebia.innovationportal.entities.Idea;
import com.xebia.innovationportal.entities.IdeaStatus;
import com.xebia.innovationportal.entities.IdeaStatusHistory;

@Repository
public interface IdeaStatusHistoryRepository extends JpaRepository<IdeaStatusHistory, Integer> {

	public IdeaStatusHistory findByIdeaAndIdeaStatus(Idea Idea, IdeaStatus ideaStatus);
}
