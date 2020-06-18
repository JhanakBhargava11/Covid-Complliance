package com.xebia.innovationportal.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.xebia.innovationportal.entities.IdeaLikeDetail;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LikeIdeaResponse {

	private Integer id;

	private String name;

	private Long userId;

	private Integer ideaId;

	public Long getUserId() {
		return userId;
	}

	public Integer getIdeaId() {
		return ideaId;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public LikeIdeaResponse(IdeaLikeDetail likeDetail) {
		super();
		this.name = likeDetail.getUser().getName();
		this.ideaId = likeDetail.getIdea().getId();
		this.userId = likeDetail.getUser().getId();
	}

}
