package com.xebia.innovationportal.models;

import com.xebia.innovationportal.entities.SubCategory;

import lombok.Getter;

@Getter
public class SubCategoryResponse {

    private Integer id;
    private String subCategoryName;
    private Integer ideaTypeId;
    private String ideaType;
    private boolean isActive;

    private SubCategoryResponse(SubCategory subCategory) {
        this.ideaTypeId = subCategory.getCategory().getId();
        this.ideaType = subCategory.getCategory().getCategoryName();
        this.subCategoryName = subCategory.getSubCategoryName();
        this.isActive = subCategory.isActive();
        this.id = subCategory.getId();
    }

    public static SubCategoryResponse of(final SubCategory subCategory) {
        return new SubCategoryResponse(subCategory);
    }

}
