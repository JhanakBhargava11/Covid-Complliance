package com.xebia.innovationportal.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.xebia.innovationportal.entities.SubCategory;
import com.xebia.innovationportal.enums.Role;
import com.xebia.innovationportal.models.SubCategoryDataRequest;
import com.xebia.innovationportal.models.UserResponse;
import com.xebia.innovationportal.models.UserSearchRequest;

public interface ManagementService {

	public Page<UserResponse> getUsers(final UserSearchRequest searchRequest);

	public boolean updateUserStatus(final Long id, final boolean status, final Role role,final Integer subCategoryId);

	public List<SubCategory> getSubCategories();

	public List<SubCategory> getActiveSubCategories(boolean isActive);

	public SubCategory saveCategory(final SubCategoryDataRequest dataRequest);

	public boolean updateCategory(final SubCategoryDataRequest dataRequest, final Integer id);

}
