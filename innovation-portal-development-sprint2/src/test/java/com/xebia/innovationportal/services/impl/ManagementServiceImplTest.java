
package com.xebia.innovationportal.services.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.xebia.innovationportal.entities.Authority;
import com.xebia.innovationportal.entities.Category;
import com.xebia.innovationportal.entities.SubCategory;
import com.xebia.innovationportal.entities.User;
import com.xebia.innovationportal.enums.Role;
import com.xebia.innovationportal.exceptions.GenericException;
import com.xebia.innovationportal.exceptions.UserNotFoundException;
import com.xebia.innovationportal.models.SubCategoryDataRequest;
import com.xebia.innovationportal.models.UserResponse;
import com.xebia.innovationportal.models.UserSearchRequest;
import com.xebia.innovationportal.repositories.CategoryRepository;
import com.xebia.innovationportal.repositories.SubCategoryRepository;
import com.xebia.innovationportal.repositories.UserRepository;

@RunWith(PowerMockRunner.class)
@SpringBootTest
public class ManagementServiceImplTest {

	@InjectMocks
	private ManagementServiceImpl managementService;

	@Mock
	private UserRepository userRepository;

	@Mock
	private SubCategoryRepository subCategoryRepository;

	@Mock
	private CategoryRepository categoryRepository;

	@Rule
	private ExpectedException expectedExceptionRule = ExpectedException.none();

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testUpdateUserStatusThrowUserNotFoundException() {
		expectedExceptionRule.expect(UserNotFoundException.class);
		when(userRepository.findById((long) 1)).thenReturn(Optional.empty());
		managementService.updateUserStatus((long) 1, true, Role.ROLE_USER,1);

	}

//	@Test
//	public void testUpdateUserStatus() {
//		User user = new User();
//		user.setDisplayName("Gourav");
//		user.setEnabled(true);
//		when(userRepository.findById((long) 1)).thenReturn(Optional.of(user));
//		assertFalse(managementService.updateUserStatus((long) 1, true, Role.ROLE_USER));
//
//	}

//	@Test
//	public void testUpdateUserStatusReturnsTrue() {
//		User user = new User();
//		user.setDisplayName("Gourav");
//		user.setEnabled(true);
//		when(userRepository.findById((long) 1)).thenReturn(Optional.of(user));
//		assertTrue(managementService.updateUserStatus((long) 1, false, Role.ROLE_USER));
//
//	}

	@Test
	public void testGetActiveSubCategories() {
		List<SubCategory> subCategories = Arrays
				.asList(SubCategory.of(1, "manager", new Category("category name", "description")));
		when(subCategoryRepository.findByIsActive(true)).thenReturn(subCategories);
		assertEquals(managementService.getActiveSubCategories(true), subCategories);
	}

//	@Test
//	public void testGetActiveSubCategoriesAll() {
//		List<SubCategory> subCategories = Arrays
//				.asList(SubCategory.of(1, "manager", new Category("category name", "description")));
//		when(this.subCategoryRepository.findAll()).thenReturn(subCategories);
//		assertEquals(managementService.getActiveSubCategories(false), subCategories);
//	}

	@Test
	public void testGetSubCategories() {
		List<SubCategory> subCategories = Arrays
				.asList(SubCategory.of(1, "manager", new Category("category name", "description")));
		when(subCategoryRepository.findAll()).thenReturn(subCategories);
		assertEquals(managementService.getSubCategories(), subCategories);

	}

	@Test
	public void testGetUsers() {
		PageRequest page = PageRequest.of(1, 12);
		UserSearchRequest searchRequest = UserSearchRequest.of("Vishal", "vishal@xebia.com", Role.ROLE_USER, page);

		User user = new User();
		user.setName("Vishal");
		user.setDisplayName("Vishal yadav");
		user.setEnabled(true);
		Set<Authority> authorities = new HashSet<>();
		Authority auth = new Authority();
		auth.setRole(Role.ROLE_USER);
		authorities.add(auth);
		user.setAuthorities(authorities);
		List<UserResponse> responses = Arrays.asList(UserResponse.of(user));

		PageImpl<UserResponse> pageImpl = new PageImpl<UserResponse>(responses, page, 1);
		when(userRepository.getUsers(searchRequest)).thenReturn(pageImpl);
		assertEquals(managementService.getUsers(searchRequest), pageImpl);

	}

	@Test
	public void testSaveCategorythrowsGenericException() {
		expectedExceptionRule.expect(GenericException.class);
		SubCategoryDataRequest request = new SubCategoryDataRequest();
		request.setCategoryId(1);
		request.setSubCategoryName("manager");
		when(this.categoryRepository.findById(request.getCategoryId())).thenReturn(Optional.empty());
		managementService.saveCategory(request);

	}

	@Test
	public void testSaveCategory() {
		SubCategoryDataRequest request = new SubCategoryDataRequest();
		request.setCategoryId(1);
		request.setSubCategoryName("manager");
		Category category = new Category();
		category.setCategoryDescription("des");
		category.setCategoryName("category name");

		when(this.categoryRepository.findById(request.getCategoryId())).thenReturn(Optional.of(category));
		managementService.saveCategory(request);
		verify(subCategoryRepository).flush();

	}

	@Test
	public void testUpdateCategory() {
		expectedExceptionRule.expect(GenericException.class);

		SubCategoryDataRequest request = new SubCategoryDataRequest();
		request.setCategoryId(1);
		request.setSubCategoryName("manager");
		Category category = new Category();
		category.setCategoryDescription("des");
		category.setCategoryName("category name");

		when(this.subCategoryRepository.findById(request.getCategoryId())).thenReturn(Optional.empty());

		managementService.updateCategory(request, 12);

	}
}
