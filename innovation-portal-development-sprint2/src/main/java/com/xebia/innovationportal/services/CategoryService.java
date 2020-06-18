package com.xebia.innovationportal.services;

import java.util.List;

import com.xebia.innovationportal.entities.AddCategory;
import com.xebia.innovationportal.entities.SubCategory;
import com.xebia.innovationportal.entities.SubCategoryResponse;

public interface CategoryService {

	List<SubCategoryResponse> getSubCategories();
	

	public SubCategory saveCategory(SubCategoryResponse response);

	/* List<SubCategory> addSubCategory(List<SubCategory> subCategories); */




	

}
