package com.xebia.innovationportal.models;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.xebia.innovationportal.entities.Idea;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class IdeaDataResponse {

    private String title;
    private String ideaDescription;
    private Integer id;
    private String ideaStatus;
    private String categoryName;
    private String subcategoryName;
    private String location;
    private LocalDate submissionDate;
    List<IdeaStatusHistoryResponse> ideaStatusHistories;
    private String submittedBy;

    private Integer categoryId;
    private Integer subCategoryId;
    private Integer likeCount;
    private String fileName;
    private String azureUrl;

    private String businessImpact;

    public String getBusinessImpact() {
        return businessImpact;
    }

    public String getFileName() {
        return fileName;
    }

    public String getAzureUrl() {
        return azureUrl;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    List<LikeIdeaResponse> likeIdeaDetailList;

    public String getSubmittedBy() {
        return submittedBy;
    }

    public List<LikeIdeaResponse> getLikeIdeaDetailList() {
        return likeIdeaDetailList;
    }

    public LocalDate getSubmissionDate() {
        return submissionDate;
    }

    public String getLocation() {
        return location;
    }

    public String getTitle() {
        return title;
    }

    public String getIdeaDescription() {
        return ideaDescription;
    }

    public String getIdeaStatus() {
        return ideaStatus;
    }

    public List<IdeaStatusHistoryResponse> getIdeaStatusHistories() {
        return ideaStatusHistories;
    }

    public Integer getId() {
        return id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getSubcategoryName() {
        return subcategoryName;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public Integer getSubCategoryId() {
        return subCategoryId;
    }

    public IdeaDataResponse() {
    }

    public IdeaDataResponse(Idea idea) {
        super();
        this.title = idea.getTitle();
        this.ideaDescription = idea.getIdeaDescription();
        this.id = idea.getId();
        this.submissionDate = idea.getSubmissionDate();
        this.submittedBy = idea.getUser().getName();
        this.likeCount = idea.getLikeCount();

        if (idea.getCategory() != null) {
            this.categoryName = idea.getCategory().getCategoryName();
            this.categoryId = idea.getCategory().getId();
        }
        if (idea.getSubCategory() != null) {
            this.subcategoryName = idea.getSubCategory().getSubCategoryName();
            this.subCategoryId = idea.getSubCategory().getId();
        }
        if (idea.getIdeaStatus() != null)
            this.ideaStatus = idea.getIdeaStatus().getStatus();
        if (idea.getLocation() != null)
            this.location = idea.getUser().getLocation();

        if (idea.getAzyurUrl() != null)
            this.azureUrl = idea.getAzyurUrl();
        if (idea.getFileName() != null)
            this.fileName = idea.getFileName();

        if (idea.getBusinessImpact() != null)
            this.businessImpact = idea.getBusinessImpact();
    }

    public IdeaDataResponse(Idea idea, boolean includeIdeaStatusHistory) {
        super();
        this.title = idea.getTitle();
        this.ideaDescription = idea.getIdeaDescription();
        this.id = idea.getId();
        this.submissionDate = idea.getSubmissionDate();
        this.submittedBy = idea.getUser().getName();
        this.likeCount = idea.getLikeCount();
        if (idea.getCategory() != null) {
            this.categoryName = idea.getCategory().getCategoryName();
            this.categoryId = idea.getCategory().getId();
        }
        if (idea.getSubCategory() != null) {
            this.subcategoryName = idea.getSubCategory().getSubCategoryName();
            this.subCategoryId = idea.getSubCategory().getId();
        }
        if (idea.getIdeaStatus() != null)
            this.ideaStatus = idea.getIdeaStatus().getStatus();
        if (idea.getLocation() != null)
            this.location = idea.getUser().getLocation();

        if (idea.getBusinessImpact() != null)
            this.businessImpact = idea.getBusinessImpact();

        List<LikeIdeaResponse> likeIdeaList = idea.getLikeIdeaList().stream().map(obj -> new LikeIdeaResponse(obj))
                .collect(Collectors.toList());

        this.likeIdeaDetailList = likeIdeaList;

        List<IdeaStatusHistoryResponse> ideaHistoryResponses = idea.getStatusHistory().stream()
                .sorted((ideaStatusHistory, ideaStatusHistory1) -> ideaStatusHistory.getModificationTime()
                        .compareTo(ideaStatusHistory1.getModificationTime()))
                .map(obj -> new IdeaStatusHistoryResponse(obj)).collect(Collectors.toList());
        this.ideaStatusHistories = ideaHistoryResponses;

        if (idea.getAzyurUrl() != null)
            this.azureUrl = idea.getAzyurUrl();
        if (idea.getFileName() != null)
            this.fileName = idea.getFileName();
    }

}
