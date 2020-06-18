package com.xebia.innovationportal.controllers;

import static com.xebia.innovationportal.constants.RestConstants.INNOVATION_PORTAL_API;
import static com.xebia.innovationportal.constants.RestConstants.Management.ACTIVE_SUB_CATEGORIES_URI;
import static com.xebia.innovationportal.constants.RestConstants.Management.SUB_CATEGORIES_BY_ID;
import static com.xebia.innovationportal.constants.RestConstants.Management.SUB_CATEGORIES_URI;
import static com.xebia.innovationportal.constants.RestConstants.Management.USER_UPDATE_STATUS_URI;
import static com.xebia.innovationportal.constants.RestConstants.Management.USER_URI;
import static com.xebia.innovationportal.utils.HeaderUtil.addError;
import static com.xebia.innovationportal.utils.HeaderUtil.addSuccess;
import static com.xebia.innovationportal.utils.RateMessageConstants.RECORD_NOT_FOUND;
import static com.xebia.innovationportal.utils.RateMessageConstants.RECORD_STATUS_UPDATED_FAILED;
import static com.xebia.innovationportal.utils.RateMessageConstants.RECORD_STATUS_UPDATED_SUCCESSFULLY;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xebia.innovationportal.enums.Role;
import com.xebia.innovationportal.models.BaseResponse;
import com.xebia.innovationportal.models.SubCategoryDataRequest;
import com.xebia.innovationportal.models.SubCategoryResponse;
import com.xebia.innovationportal.models.UserResponse;
import com.xebia.innovationportal.models.UserSearchRequest;
import com.xebia.innovationportal.services.ManagementService;
import com.xebia.innovationportal.utils.ImmutableCollectors;
import com.xebia.innovationportal.utils.PaginatedResource;
import com.xebia.innovationportal.utils.PaginatedResourceAssembler;

@RestController
@RequestMapping(INNOVATION_PORTAL_API)
@Validated
public class ManagementController {

	@Autowired
	private ManagementService managementService;

	@Autowired
	private PaginatedResourceAssembler<UserResponse> pagedResourcesAssembler;

	@GetMapping(USER_URI)
	public ResponseEntity<BaseResponse<PaginatedResource<UserResponse>>> getUsers(
			@RequestParam(value = "name", required = false) final String name,
			@Email @RequestParam(value = "email", required = false) final String email,
			@RequestParam(name = "roleType", required = false) final Role role,
			@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "15") Integer size) {
		Page<UserResponse> userResponse = managementService
				.getUsers(UserSearchRequest.of(name, email, role, PageRequest.of(page, size)));
		return new ResponseEntity<>(
				new BaseResponse<PaginatedResource<UserResponse>>(pagedResourcesAssembler.assemble(userResponse)),
				HttpStatus.OK);
	}

	@PutMapping(USER_UPDATE_STATUS_URI)
	public ResponseEntity<Void> updateUserRoleOrStatus(
			@PathVariable(name = "id", required = true) @NotNull final Long id,
			@RequestParam(name = "roleType", required = false) final Role role,
			@RequestParam(name = "subCategoryId", required = false) final Integer subCategoryId,
			@RequestParam(value = "status", required = true) final boolean status) {

		this.managementService.updateUserStatus(id, status, role, subCategoryId);
		return ResponseEntity.ok().headers(addSuccess(RECORD_STATUS_UPDATED_SUCCESSFULLY)).build();

	}

	@GetMapping(SUB_CATEGORIES_URI)
	public ResponseEntity<BaseResponse<List<SubCategoryResponse>>> getCategories() {
		List<SubCategoryResponse> response = this.managementService.getSubCategories().stream()
				.map(SubCategoryResponse::of).collect(ImmutableCollectors.toImmutableList());
		if (response.isEmpty()) {
			return ResponseEntity.notFound().headers(addError(RECORD_NOT_FOUND)).build();
		}
		return new ResponseEntity<BaseResponse<List<SubCategoryResponse>>>(
				new BaseResponse<List<SubCategoryResponse>>(response), HttpStatus.OK);
	}

	@GetMapping(ACTIVE_SUB_CATEGORIES_URI)
	public ResponseEntity<List<SubCategoryResponse>> getActiveCategories(
			@RequestParam(value = "isActive", required = true) final Boolean isActive) {
		List<SubCategoryResponse> response = this.managementService.getActiveSubCategories(isActive).stream()
				.map(SubCategoryResponse::of).collect(ImmutableCollectors.toImmutableList());

		if (response.isEmpty()) {
			return ResponseEntity.notFound().headers(addError(RECORD_NOT_FOUND)).build();
		}
		return ResponseEntity.ok(response);
	}

	@PostMapping(SUB_CATEGORIES_URI)
	public ResponseEntity<Void> addSubCategory(@Valid @RequestBody SubCategoryDataRequest dataRequest) {
		Integer id = this.managementService.saveCategory(dataRequest).getId();
		return new ResponseEntity<Void>(id == null ? HttpStatus.BAD_REQUEST : HttpStatus.CREATED);
	}

	@PutMapping(SUB_CATEGORIES_BY_ID)
	public ResponseEntity<Void> updatedSubCategory(@PathVariable(name = "id", required = true) @NotNull Integer id,
			@Valid @RequestBody SubCategoryDataRequest dataRequest) {
		boolean updated = this.managementService.updateCategory(dataRequest, id);

		return ResponseEntity.ok().headers(
				updated ? addSuccess(RECORD_STATUS_UPDATED_SUCCESSFULLY) : addError(RECORD_STATUS_UPDATED_FAILED))
				.build();
	}

}
