package com.xebia.innovationportal.repositories;

import org.springframework.data.domain.Page;

import com.xebia.innovationportal.models.UserResponse;
import com.xebia.innovationportal.models.UserSearchRequest;

public interface UserRepositoryJPA extends JPA{
	
	public Page<UserResponse> getUsers(final UserSearchRequest searchRequest);

}
