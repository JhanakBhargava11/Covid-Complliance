package com.xebia.innovationportal.repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.xebia.innovationportal.constants.CommonConstants;
import com.xebia.innovationportal.entities.Idea;
import com.xebia.innovationportal.entities.IdeaStatus;
import com.xebia.innovationportal.entities.User;

@Repository
@Transactional
public interface IdeaRepository extends JpaRepository<Idea, Integer> {

	@Query(value = "select * from ideas where ideas.status_id in (select id from idea_status where idea_status.status in ('"
			+ CommonConstants.STATUS_APPROVED + "','" + CommonConstants.STATUS_DEVELOPMENT
			+ "')) order by ideas.like_count desc limit 5", nativeQuery = true)
	public List<Idea> findIdeaByLikesCount();

	public List<Idea> findByIdeaStatusIdNotIn(Collection<Integer> ideaStatus);

	public List<Idea> findByUser(User user);

	public List<Idea> findByIdeaStatusOrderBySubmissionDateDesc(IdeaStatus ideaStatus);

	public List<Idea> findByIdeaStatusIdNotInOrderBySubmissionDateDesc(Collection<Integer> ideaStatus);

}
