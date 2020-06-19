package com.xebia.innovationportal.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xebia.innovationportal.entities.Category;
import com.xebia.innovationportal.entities.SubCategory;
import com.xebia.innovationportal.entities.SubCategoryResponse;
import com.xebia.innovationportal.repositories.CategoryRepository;
import com.xebia.innovationportal.repositories.SubCategoryRepository;
import com.xebia.innovationportal.services.CategoryService;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private CategoryRepository respository;

    @Override
    public List<SubCategoryResponse> getSubCategories() {

        List<SubCategory> dbList = subCategoryRepository.findAll();
        List<SubCategoryResponse> subCategoryResponseList = new ArrayList<SubCategoryResponse>();
        for (SubCategory subCategory : dbList) {
            SubCategoryResponse subCategoryResponse = new SubCategoryResponse();
            subCategoryResponse.setId(subCategory.getId());
            subCategoryResponse.setCategoryName(subCategory.getSubCategoryName());
            subCategoryResponse.setCategoryId(subCategory.getCategory().getId());
            // subCategoryResponse.setActive(isActive);
            subCategoryResponse.setSubCategoryName(subCategory.getCategory().getCategoryName());

            subCategoryResponseList.add(subCategoryResponse);

        }

        return subCategoryResponseList;
    }

    public SubCategory saveCategory(SubCategoryResponse response) {
        SubCategory subCategory = new SubCategory();
        Category category = respository.getOne(response.getCategoryId());
        subCategory.setCategory(category);
        subCategory.setSubCategoryName(response.getCategoryName());
        return subCategoryRepository.save(subCategory);
    }

}
