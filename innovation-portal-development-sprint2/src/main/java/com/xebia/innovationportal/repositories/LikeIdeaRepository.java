package com.xebia.innovationportal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xebia.innovationportal.entities.Idea;
import com.xebia.innovationportal.entities.IdeaLikeDetail;
import com.xebia.innovationportal.entities.User;

@Repository
public interface LikeIdeaRepository extends JpaRepository<IdeaLikeDetail, Integer> {

	public IdeaLikeDetail findByIdeaAndUser(Idea idea, User user);

}
