
package com.xebia.innovationportal.repositories;

import java.util.Collections;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;

import com.xebia.innovationportal.entities.User;
import com.xebia.innovationportal.entities.User_;
import com.xebia.innovationportal.models.UserResponse;
import com.xebia.innovationportal.models.UserSearchRequest;
import com.xebia.innovationportal.utils.ImmutableCollectors;

@Repository
public class UserRepositoryImpl extends AbstractJPA implements UserRepositoryJPA {

	@Override
	public Page<UserResponse> getUsers(final UserSearchRequest searchRequest) {

		long totalRecords = findUsersCount(searchRequest);
		if (totalRecords == 0) {
			return new PageImpl<>(Collections.emptyList(), searchRequest.getPageable(), totalRecords);
		} else {

			CriteriaBuilder criteriaBuilder = criteriaBuilder();
			CriteriaQuery<User> query = criteriaQuery(User.class);
			Root<User> rate = query.from(User.class);

			query.where(UserPredicate.getUserPredicates(criteriaBuilder, rate, searchRequest.getName(),
					searchRequest.getEmail(), searchRequest.getRole()));
			query.orderBy(criteriaBuilder.asc(rate.get(User_.name.getName())));
			query.select(rate);

			TypedQuery<User> typedQuery = typedQuery(query);
			typedQuery.setFirstResult(
					searchRequest.getPageable().getPageNumber() * searchRequest.getPageable().getPageSize());
			typedQuery.setMaxResults(searchRequest.getPageable().getPageSize());

			List<User> results = getUnmodifiableResultList(typedQuery);

			List<UserResponse> responses = results.stream().map(UserResponse::of)
					.collect(ImmutableCollectors.toImmutableList());

			return new PageImpl<>(responses, searchRequest.getPageable(), totalRecords);

		}

	}

	private long findUsersCount(UserSearchRequest searchRequest) {

		CriteriaBuilder criteriaBuilder = criteriaBuilder();
		CriteriaQuery<Long> query = criteriaQuery(Long.class);
		Root<User> rate = query.from(User.class);
		query.select(criteriaBuilder.countDistinct(rate));
		return typedQuery(query).getSingleResult().longValue();
	}

}
