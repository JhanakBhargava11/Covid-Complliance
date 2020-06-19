package com.xebia.innovationportal.models;

public class IdeaStatsResponse {

    private String location;

    private int submitted = 0;
    private int approved = 0;
    private int development = 0;
    private int completed = 0;

    public IdeaStatsResponse(String location) {
        super();
        this.location = location;

    }

    public String getLocation() {
        return location;
    }

    public int getApprovedCount() {
        return approved;
    }

    public void setApprovedCount(int approvedCount) {
        this.approved = approvedCount;
    }

    public void setSubmittedCount(int submittedCount) {
        this.submitted = submittedCount;
    }

    public void setDevelopmentCount(int developmentCount) {
        this.development = developmentCount;
    }

    public void setCompletedCount(int completedCount) {
        this.completed = completedCount;
    }

    public int getSubmittedCount() {
        return submitted;
    }


    public int getDevelopmentCount() {
        return development;
    }

    public int getCompletedCount() {
        return completed;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((location == null) ? 0 : location.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        IdeaStatsResponse other = (IdeaStatsResponse) obj;
        if (location == null) {
            if (other.location != null)
                return false;
        } else if (!location.equals(other.location))
            return false;

        return true;
    }

}
