package com.xebia.innovationportal.services.impl;

import static com.xebia.innovationportal.constants.ExceptionConstants.ACTIVE_USER_NOT_FOUND;
import static com.xebia.innovationportal.constants.ExceptionConstants.INVALID_SUB_CATEGORYID;
import static com.xebia.innovationportal.constants.ExceptionConstants.INVALID_ROLE_TYPE;
import static com.xebia.innovationportal.constants.ExceptionConstants.ROLE_NOT_FOUND;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xebia.innovationportal.constants.CommonConstants;
import com.xebia.innovationportal.entities.Category;
import com.xebia.innovationportal.entities.SubCategory;
import com.xebia.innovationportal.entities.User;
import com.xebia.innovationportal.entities.UsersAuthorities;
import com.xebia.innovationportal.enums.Role;
import com.xebia.innovationportal.exceptions.GenericException;
import com.xebia.innovationportal.exceptions.InvalidRoleTypeException;
import com.xebia.innovationportal.exceptions.RoleNotFoundException;
import com.xebia.innovationportal.exceptions.UserNotFoundException;
import com.xebia.innovationportal.models.SubCategoryDataRequest;
import com.xebia.innovationportal.models.UserResponse;
import com.xebia.innovationportal.models.UserSearchRequest;
import com.xebia.innovationportal.repositories.CategoryRepository;
import com.xebia.innovationportal.repositories.SubCategoryRepository;
import com.xebia.innovationportal.repositories.UserAuthoritiesRepository;
import com.xebia.innovationportal.repositories.UserRepository;
import com.xebia.innovationportal.services.ManagementService;

@Service
public class ManagementServiceImpl implements ManagementService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserAuthoritiesRepository userAuthoritiesRepository;

    @Transactional(readOnly = true)
    @Override
    public Page<UserResponse> getUsers(final UserSearchRequest searchRequest) {
        return this.userRepository.getUsers(searchRequest);

    }

    @Transactional
    @Override
    public boolean updateUserStatus(final Long id, final boolean status, final Role role, final Integer subCategoryId) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(ACTIVE_USER_NOT_FOUND + id));
        if (role != null && status == true) {
            Optional<UsersAuthorities> userAuthorities = this.userAuthoritiesRepository.findById(id);
            if (userAuthorities.isPresent()) {
                Integer roleId = getRoleId(role);
                if (roleId != null) {
                    if (role == Role.ROLE_MANAGER) {

                        SubCategory subCategory = this.subCategoryRepository.findById(subCategoryId).orElseThrow(() -> new GenericException(INVALID_SUB_CATEGORYID));
                        user.setSubCategory(subCategory);
                    } else {
                        user.setSubCategory(null);
                    }
                    userAuthorities.get().setAuthorityId(roleId);
                } else {
                    throw new InvalidRoleTypeException(INVALID_ROLE_TYPE);
                }
            } else {
                throw new RoleNotFoundException(ROLE_NOT_FOUND + id);
            }
        }
        if (user.isEnabled() != status) {
            user.updateStatus(status);
        }

        return true;
    }

    private Integer getRoleId(final Role role) {
        Integer roleId = null;
        switch (role) {
            case ROLE_ADMIN:
                roleId = CommonConstants.ROLE_ADMIN_ID;
                break;
            case ROLE_MANAGER:
                roleId = CommonConstants.ROLE_MANAGER_ID;
                break;
            case ROLE_USER:
                roleId = CommonConstants.ROLE_USER_ID;
                break;
            default:
                break;
        }
        return roleId;
    }

    @Transactional(readOnly = true)
    @Override
    public List<SubCategory> getSubCategories() {
        return subCategoryRepository.findAll();
    }

    @Override
    public List<SubCategory> getActiveSubCategories(final boolean isActive) {
        return subCategoryRepository.findByIsActive(isActive);
    }

    @Override
    @Transactional
    public SubCategory saveCategory(final SubCategoryDataRequest dataRequest) {
        Category category = this.categoryRepository.findById(dataRequest.getCategoryId())
                .orElseThrow(() -> new GenericException(HttpStatus.NOT_FOUND));
        SubCategory subCategory = SubCategory.of(null, dataRequest.getSubCategoryName(), category);
        SubCategory newSubCategory = this.subCategoryRepository.save(subCategory);
        this.subCategoryRepository.flush();
        return newSubCategory;

    }

    @Override
    @Transactional
    public boolean updateCategory(final SubCategoryDataRequest dataRequest, Integer id) throws GenericException {

        SubCategory existSubCategory = this.subCategoryRepository.findById(id)
                .orElseThrow(() -> new GenericException(HttpStatus.NOT_FOUND));
        Category category = this.categoryRepository.findById(dataRequest.getCategoryId())
                .orElseThrow(() -> new GenericException(HttpStatus.NOT_FOUND));
        existSubCategory.setActive(true);
        existSubCategory.setSubCategoryName(dataRequest.getSubCategoryName());
        existSubCategory.setCategory(category);
        existSubCategory.setActive(dataRequest.getIsActive());
        this.subCategoryRepository.save(existSubCategory);
        this.subCategoryRepository.flush();
        return true;

    }

}
