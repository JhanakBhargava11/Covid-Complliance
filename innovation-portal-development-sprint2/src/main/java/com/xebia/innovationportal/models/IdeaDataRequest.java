package com.xebia.innovationportal.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Setter;

@Setter
public class IdeaDataRequest {

    @NotEmpty
    private String title;

    private String ideaDescription;

    private Integer id;

    @NotNull
    private Integer categoryId;

    @NotNull
    private Integer subCategoryId;

    @NotNull
    private Integer ideaStatusId;

    private String fileName;

    private String azureUrl;

    @NotEmpty
    private String businessImpact;

    public String getFileName() {
        return fileName;
    }

    public String getAzureUrl() {
        return azureUrl;
    }

    public IdeaDataRequest() {

    }

    public String getTitle() {
        return title;
    }

    public String getIdeaDescription() {
        return ideaDescription;
    }

    public Integer getId() {
        return id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public Integer getSubCategoryId() {
        return subCategoryId;
    }

    public Integer getIdeaStatusId() {
        return ideaStatusId;
    }

    private IdeaDataRequest(Builder builder) {
        title = builder.title;
        ideaDescription = builder.ideaDescription;
        id = builder.id;
        categoryId = builder.categoryId;
        subCategoryId = builder.subCategoryId;
        ideaStatusId = builder.ideaStatusId;
        fileName = builder.fileName;
        azureUrl = builder.azureUrl;
    }

    public static final class Builder {
        private @NotEmpty String title;
        private String ideaDescription;
        private Integer id;
        private @NotNull Integer categoryId;
        private @NotNull Integer subCategoryId;
        private @NotNull Integer ideaStatusId;

        private String fileName;
        private String azureUrl;

        public Builder() {
        }

        public Builder withTitle(@NotEmpty String val) {
            title = val;
            return this;
        }

        public Builder withIdeaDescription(String val) {
            ideaDescription = val;
            return this;
        }

        public Builder withFileName(String val) {
            fileName = val;
            return this;
        }

        public Builder withAzureUrl(String val) {
            azureUrl = val;
            return this;
        }

        public Builder withId(Integer val) {
            id = val;
            return this;
        }

        public Builder withCategoryId(@NotNull Integer val) {
            categoryId = val;
            return this;
        }

        public Builder withSubCategoryId(@NotNull Integer val) {
            subCategoryId = val;
            return this;
        }

        public Builder withIdeaStatusId(@NotNull Integer val) {
            ideaStatusId = val;
            return this;
        }

        public IdeaDataRequest build() {
            return new IdeaDataRequest(this);
        }
    }

    @Override
    public String toString() {
        return "IdeaDataRequest [title=" + title + ", ideaDescription=" + ideaDescription + ", id=" + id
                + ", categoryId=" + categoryId + ", subCategoryId=" + subCategoryId + ", ideaStatusId=" + ideaStatusId
                + ", fileName=" + fileName + ", azureUrl=" + azureUrl + "]";
    }

    public String getBusinessImpact() {
        return businessImpact;
    }

}
