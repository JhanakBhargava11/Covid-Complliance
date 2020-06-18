package com.xebia.innovationportal.services.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xebia.innovationportal.constants.CommonConstants;
import com.xebia.innovationportal.entities.Idea;
import com.xebia.innovationportal.entities.IdeaStatus;
import com.xebia.innovationportal.entities.User;
import com.xebia.innovationportal.models.IdeaStatsResponse;
import com.xebia.innovationportal.models.TopContributorResponse;
import com.xebia.innovationportal.repositories.IdeaRepository;
import com.xebia.innovationportal.services.DashboardService;

@Service
@Transactional
public class DashboardServiceImpl implements DashboardService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DashboardServiceImpl.class);

	@Autowired
	private IdeaRepository ideaRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<TopContributorResponse> getTopContributor() {
		LOGGER.info("*********** Start Fetching top contributors *************");
		List<Idea> submittedIdea = ideaRepository.findByIdeaStatusIdNotIn(Collections.singletonList(1));
		Map<User, TopContributorResponse> ideasByUser = new HashMap<>();
		TopContributorResponse availableList, newIdeas;
		for (Idea temp : submittedIdea) {
			User user = temp.getUser();
			if (ideasByUser.containsKey(user)) {
				availableList = ideasByUser.get(user);
				availableList.addIdea(temp);
				ideasByUser.put(user, availableList);
			} else {
				newIdeas = new TopContributorResponse();
				newIdeas.setUser(
						new TopContributorResponse.User(temp.getUser().getName(), temp.getUser().getDesignation()));
				newIdeas.addIdea(temp);
				ideasByUser.put(user, newIdeas);
			}
		}

		List<TopContributorResponse> responses = new ArrayList<>();
		List<Map.Entry<User, TopContributorResponse>> topList = new ArrayList<>(ideasByUser.entrySet());
		int size = topList.size();
		if (size > 5)
			size = size - 5;
		else
			size = 0;

		topList.sort((Comparator.comparingInt(o -> o.getValue().getTopcontributing())));
		for (int i = topList.size() - 1; i >= size; i--) {
			responses.add(topList.get(i).getValue());
		}
		return responses;
	}

	@Override
	public List<Idea> getTrendingIdeas() {
		LOGGER.info("*********** Start Fetching top trending Ideas *************");
		return this.ideaRepository.findIdeaByLikesCount();

	}

	@Override
	public HashSet<IdeaStatsResponse> getAllIdeaStats(String duration) {

		HashSet<IdeaStatsResponse> responses = new HashSet<IdeaStatsResponse>();
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
		Root<Idea> root = query.from(Idea.class);
		Join<Idea, IdeaStatus> idea_statusJoin = root.join("ideaStatus");
		query.multiselect(root.get("location"), idea_statusJoin.get("status"), builder.count(root.get("location")));

		if (!duration.equalsIgnoreCase("all"))
			query.where(builder.greaterThan(root.get("creationTime"),
					LocalDateTime.now().minusDays(getDurationInInteger(duration))));

		query.groupBy(root.get("location"), idea_statusJoin.get("status"));

		List<Object[]> result = entityManager.createQuery(query).getResultList();
		for (Object[] objects : result) {
			String location = (String) objects[0];
			String status_name = (String) objects[1];
			int noOfRecords = new Long((long) objects[2]).intValue();
			IdeaStatsResponse current = new IdeaStatsResponse(location);
			for (IdeaStatsResponse temp : responses) {
				if (temp.equals(current)) {
					current = temp;
					break;
				}
			}
			if (CommonConstants.STATUS_COMPLETED.equals(status_name))
				current.setCompletedCount(noOfRecords);
			else if (CommonConstants.STATUS_APPROVED.equals(status_name))
				current.setApprovedCount(noOfRecords);
			else if (CommonConstants.STATUS_SUBMITTED.equals(status_name))
				current.setSubmittedCount(noOfRecords);
			else if (CommonConstants.STATUS_DEVELOPMENT.equals(status_name))
				current.setDevelopmentCount(noOfRecords);

			responses.add(current);
		}
		return responses;
	}

	private Integer getDurationInInteger(String duration) {
		if (duration.equalsIgnoreCase("Quarter"))
			return 90;
		else if (duration.equalsIgnoreCase("Year"))
			return 365;
		else
			return 30;

	}
}
