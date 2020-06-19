package com.xebia.innovationportal.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ideas")
public class Idea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDEA_ID")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    @OneToOne
    @JoinColumn(name = "SUB_CATEGORY_ID")
    private SubCategory subCategory;

    @OneToOne
    @JoinColumn(name = "STATUS_ID")
    private IdeaStatus ideaStatus;

    @Column(length = 500, nullable = false)
    private String ideaDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(length = 255, nullable = false)
    private String title;

    @Column(length = 300, nullable = false)
    private String businessImpact;

    private String fileName;

    private String azureUrl;

    private String assignTo;

    private int likeCount;

    @OneToMany(mappedBy = "idea", cascade = CascadeType.ALL)
    private List<IdeaStatusHistory> statusHistory = new ArrayList<IdeaStatusHistory>();

    @OneToMany(mappedBy = "idea", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<IdeaLikeDetail> likeIdeaList = new LinkedList<IdeaLikeDetail>();

    private LocalDate submissionDate;
    private LocalDateTime creationTime;

    private String location;

    public Idea() {
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public String getAzureUrl() {
        return azureUrl;
    }

    public String getBusinessImpact() {
        return businessImpact;
    }

    public void setBusinessImpact(String businessImpact) {
        this.businessImpact = businessImpact;
    }

    private Idea(Builder builder) {
        setCategory(builder.category);
        setSubCategory(builder.SubCategory);
        setIdeaStatus(builder.ideaStatus);
        setIdeaDescription(builder.ideaDescription);
        setUser(builder.user);
        setTitle(builder.title);
        setFileName(builder.fileName);
        setAzureUrl(builder.azureUrl);
        setBusinessImpact(builder.businessImpact);
        setAssignTo(builder.assignTo);
        setLikeCount(builder.likeCount);
        setStatusHistory(builder.statusHistory);
        setLikeIdeaList(builder.likeIdeaDetailList);
        setSubmissionDate(builder.submissionDate);
        setLocation(builder.location);
        setCreationTime(builder.creationTime);
    }

    public List<IdeaStatusHistory> getStatusHistory() {
        return statusHistory;
    }

    public void setStatusHistory(List<IdeaStatusHistory> statusHistory) {
        this.statusHistory = statusHistory;
    }

    public List<IdeaLikeDetail> getLikeIdeaList() {
        return likeIdeaList;
    }

    public void setLikeIdeaList(List<IdeaLikeDetail> likeIdeaList) {
        this.likeIdeaList = likeIdeaList;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setAzureUrl(String azureUrl) {
        this.azureUrl = azureUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAzyurUrl() {
        return azureUrl;
    }

    public String getIdeaDescription() {
        return ideaDescription;
    }

    public void setIdeaDescription(String ideaDescription) {
        this.ideaDescription = ideaDescription;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public IdeaStatus getIdeaStatus() {
        return ideaStatus;
    }

    public void setIdeaStatus(IdeaStatus ideaStatus) {
        this.ideaStatus = ideaStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public SubCategory getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
    }

    public String getAssignTo() {
        return assignTo;
    }

    public void setAssignTo(String assignTo) {
        this.assignTo = assignTo;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public LocalDate getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(LocalDate submissionDate) {
        this.submissionDate = submissionDate;
    }

    public static final class Builder {
        private Category category;
        private SubCategory SubCategory;
        private IdeaStatus ideaStatus;
        private String ideaDescription;
        private User user;
        private String title;
        private String fileName;
        private String azureUrl;
        private String assignTo;
        private int likeCount;
        private List<IdeaStatusHistory> statusHistory;
        private List<IdeaLikeDetail> likeIdeaDetailList;
        private LocalDate submissionDate;
        private String location;
        private LocalDateTime creationTime;
        private String businessImpact;

        public Builder() {
        }

        public Builder withCategory(Category val) {
            category = val;
            return this;
        }

        public Builder withBusinessImpact(String val) {
            businessImpact = val;
            return this;
        }

        public Builder withSubCategory(SubCategory val) {
            SubCategory = val;
            return this;
        }

        public Builder withIdeaStatus(IdeaStatus val) {
            ideaStatus = val;
            return this;
        }

        public Builder withIdeaDescription(String val) {
            ideaDescription = val;
            return this;
        }

        public Builder withUser(User val) {
            user = val;
            return this;
        }

        public Builder withTitle(String val) {
            title = val;
            return this;
        }

        public Builder withFileName(String val) {
            fileName = val;
            return this;
        }

        public Builder withAzyurUrl(String val) {
            azureUrl = val;
            return this;
        }

        public Builder withAssignTo(String val) {
            assignTo = val;
            return this;
        }

        public Builder withLikeCount(int val) {
            likeCount = val;
            return this;
        }

        public Builder withStatusHistory(List<IdeaStatusHistory> val) {
            statusHistory = val;
            return this;
        }

        public Builder withLikeIdeaList(List<IdeaLikeDetail> val) {
            likeIdeaDetailList = val;
            return this;
        }

        public Builder withCreationTime(LocalDateTime val) {
            creationTime = val;
            return this;
        }

        public Builder withLocation(String val) {
            location = val;
            return this;
        }

        public Idea build() {
            return new Idea(this);
        }
    }
}
