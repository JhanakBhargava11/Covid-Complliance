package com.xebia.innovationportal.models;

import com.sun.istack.NotNull;

import lombok.Getter;

@Getter
public class IdeaStatusUpdateRequest {

    @NotNull
    private Integer ideaId;
    @NotNull
    private Integer statusId;

    private String comment;

}
