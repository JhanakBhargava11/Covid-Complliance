package com.xebia.innovationportal.models;

import com.xebia.innovationportal.entities.Idea;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class TopTrendingIdeasResponse {
    private String title;
    private String submittedBy;
    private int likeCount;
    private int ideaId;

    private TopTrendingIdeasResponse(String title, String submittedBy, int likeCount, int ideaId) {
        this.title = title;
        this.submittedBy = submittedBy;
        this.likeCount = likeCount;
        this.ideaId = ideaId;
    }

    public static TopTrendingIdeasResponse of(final Idea idea) {
        return new TopTrendingIdeasResponse(idea.getTitle(), idea.getUser().getName(), idea.getLikeCount(),
                idea.getId());
    }
}
