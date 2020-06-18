package com.xebia.innovationportal.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SubCategoryDataRequest {

	@NotNull
	private Integer categoryId;
	@NotEmpty
	@NotNull
	private String subCategoryName;

	private Boolean isActive;

}
