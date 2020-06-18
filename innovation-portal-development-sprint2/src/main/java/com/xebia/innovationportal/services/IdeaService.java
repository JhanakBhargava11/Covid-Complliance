package com.xebia.innovationportal.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.xebia.innovationportal.entities.Idea;
import com.xebia.innovationportal.entities.User;
import com.xebia.innovationportal.exceptions.GenericException;
import com.xebia.innovationportal.models.IdeaDataRequest;
import com.xebia.innovationportal.models.IdeaDataResponse;
import com.xebia.innovationportal.models.IdeaStatusUpdateRequest;

public interface IdeaService {

	public static Authentication authentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	public static User getUser() {
		return (User) authentication().getPrincipal();
	}

	public Page<IdeaDataResponse> findAllIdeas(Pageable pageable);

	public Integer saveIdea(IdeaDataRequest data) throws GenericException;

	public void updateIdea(Integer id, IdeaDataRequest data) throws GenericException;

	public Integer dislikeIdea(Integer ideaId, Long userId) throws GenericException;

	public IdeaDataResponse findIdeaById(Integer ideaId) throws GenericException;

	public Integer likeIdea(Integer ideaId, Long userId) throws GenericException;

	public List<Idea> findAllIdeaByStatus(String status) throws GenericException;

	public Page<IdeaDataResponse> getIdeaByUserId(Long user, Pageable pageable) throws GenericException;

	public List<Idea> findAllIdeaRequestByStatus(String status) throws GenericException;

	public Page<IdeaDataResponse> findAllIdeasRequest(Pageable pageable, String status);

	public Integer updateIdeaByStatus(IdeaStatusUpdateRequest request);

}
