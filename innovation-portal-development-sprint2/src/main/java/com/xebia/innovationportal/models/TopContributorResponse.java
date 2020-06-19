package com.xebia.innovationportal.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xebia.innovationportal.entities.Idea;

public class TopContributorResponse {

    @JsonIgnore
    private List<Idea> ideas;
    private int totalIdeas;
    private int approvedIdeas = 0;
    @JsonIgnore
    private int topcontributing = 0;

    private User user;

    public TopContributorResponse(String username, List<Idea> ideas, int totalIdeas, int approvedIdeas,
                                  String designation) {
        this.user = new User(username, designation);
        this.ideas = ideas;
        this.totalIdeas = totalIdeas;
        this.approvedIdeas = approvedIdeas;
    }

    public int getApprovedIdeas() {
        return approvedIdeas;
    }

    public TopContributorResponse() {
        this.ideas = new ArrayList<Idea>();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Idea> getIdeas() {
        return ideas;
    }

    public void setIdeas(List<Idea> ideas) {
        this.ideas = ideas;
    }

    public int getTotalIdeas() {
        return totalIdeas;
    }

    public void setTotalIdeas(int totalIdeas) {
        this.totalIdeas = totalIdeas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        TopContributorResponse that = (TopContributorResponse) o;
        return totalIdeas == that.totalIdeas && approvedIdeas == that.approvedIdeas && Objects.equals(user, that.user)
                && Objects.equals(ideas, that.ideas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, ideas, totalIdeas, approvedIdeas);
    }

    public int getTopcontributing() {
        return topcontributing;
    }

    public void addIdea(Idea idea) {
        this.ideas.add(idea);
        this.totalIdeas = this.ideas.size();
        if (Arrays.asList("Approved", "Completed", "Development").contains(idea.getIdeaStatus().getStatus())) {
            this.topcontributing++;
        }
        if (idea.getIdeaStatus().getStatus().equalsIgnoreCase("Approved")) {
            this.approvedIdeas++;
        }
    }

    public static class User {
        private String username;
        private String designation;

        public User(String username, String designation) {
            this.username = username;
            this.designation = designation;
        }

        public String getDesignation() {
            return designation;
        }

        public String getUsername() {
            return username;
        }

    }

}
