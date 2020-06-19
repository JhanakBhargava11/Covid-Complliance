package com.xebia.innovationportal.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "IdeaStatusHistories")
public class IdeaStatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "STATUS_ID")
    private IdeaStatus ideaStatus;

    @ManyToOne
    @JoinColumn(name = "IDEA_ID")
    private Idea idea;

    @Column(name = "COMMENTS")
    private String comment;

    @OneToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getModificationTime() {
        return modificationTime;
    }

    public void setModificationTime(LocalDateTime modificationTime) {
        this.modificationTime = modificationTime;
    }

    private LocalDateTime modificationTime;

    public Integer getId() {
        return id;
    }

    public IdeaStatus getIdeaStatus() {
        return ideaStatus;
    }

    public void setIdeaStatus(IdeaStatus ideaStatus) {
        this.ideaStatus = ideaStatus;
    }

    public Idea getIdea() {
        return idea;
    }

    public void setIdea(Idea idea) {
        this.idea = idea;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
