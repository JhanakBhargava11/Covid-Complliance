package com.xebia.innovationportal.models;

import java.time.LocalDateTime;

import com.xebia.innovationportal.entities.IdeaStatusHistory;

public class IdeaStatusHistoryResponse {

    private String ideaStatus;
    private String comment;
    private String name;
    private Long userId;

    private LocalDateTime modificationTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getModificationTime() {
        return modificationTime;
    }

    public void setModificationTime(LocalDateTime modificationTime) {
        this.modificationTime = modificationTime;
    }

    public String getIdeaStatus() {
        return ideaStatus;
    }

    public void setIdeaStatus(String ideaStatus) {
        this.ideaStatus = ideaStatus;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public IdeaStatusHistoryResponse() {

    }

    public IdeaStatusHistoryResponse(IdeaStatusHistory ideaStatusHistory) {
        super();
        this.ideaStatus = ideaStatusHistory.getIdeaStatus().getStatus();
        this.comment = ideaStatusHistory.getComment() != null ? ideaStatusHistory.getComment() : "";
        this.modificationTime = ideaStatusHistory.getModificationTime();
        this.name = ideaStatusHistory.getUser().getName();
        this.userId = ideaStatusHistory.getUser().getId();
    }

}
