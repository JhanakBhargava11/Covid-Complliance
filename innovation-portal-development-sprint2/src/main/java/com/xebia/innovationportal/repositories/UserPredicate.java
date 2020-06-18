package com.xebia.innovationportal.repositories;

import static com.xebia.innovationportal.services.IdeaService.getUser;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.xebia.innovationportal.constants.CommonConstants;
import com.xebia.innovationportal.entities.Authority;
import com.xebia.innovationportal.entities.Authority_;
import com.xebia.innovationportal.entities.Idea;
import com.xebia.innovationportal.entities.IdeaStatus;
import com.xebia.innovationportal.entities.IdeaStatus_;
import com.xebia.innovationportal.entities.Idea_;
import com.xebia.innovationportal.entities.SubCategory;
import com.xebia.innovationportal.entities.SubCategory_;
import com.xebia.innovationportal.entities.User;
import com.xebia.innovationportal.entities.User_;
import com.xebia.innovationportal.enums.Role;

public class UserPredicate {

	public static Predicate[] getUserPredicates(CriteriaBuilder criteriaBuilder, Root<User> root, String name,
			String email, Role role) {
		List<Predicate> predicates = new ArrayList<>();
		if (name != null) {
			predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get(User_.name.getName())),
					name.toUpperCase() + "%"));
		}
		if (email != null) {
			predicates.add(criteriaBuilder.equal(root.get(User_.email.getName()), email));
		}
		if (role != null) {

			Join<User, Authority> userAthourity = root.join(User_.authorities);

			predicates.add(criteriaBuilder.equal(userAthourity.get(Authority_.role.getName()), role));
		}
		return predicates.toArray(new Predicate[predicates.size()]);

	}

	public static Predicate[] getUserPredicateByRole(CriteriaBuilder criteriaBuilder, Root<Idea> root, String status,
			Role role) {
		List<Predicate> predicateList = new ArrayList<>();
		Join<Idea, IdeaStatus> idea_statusJoin = root.join(Idea_.ideaStatus);

		if (!"all".equals(status)) {
			predicateList.add(criteriaBuilder.equal(idea_statusJoin.get(IdeaStatus_.status.getName()), status));
		} else {
			predicateList.add(criteriaBuilder.notEqual(idea_statusJoin.get(IdeaStatus_.status.getName()),
					CommonConstants.STATUS_DRAFT));
		}

		if (role.description().equalsIgnoreCase(Role.ROLE_ADMIN.description()))
			return predicateList.toArray(new Predicate[predicateList.size()]);
		else {

			Integer subCategoryId = getUser().getSubCategory().getId();
			predicateList.add(criteriaBuilder.equal(root.get(Idea_.subCategory.getName()), subCategoryId));
			return predicateList.toArray(new Predicate[predicateList.size()]);
		}

	}

}
