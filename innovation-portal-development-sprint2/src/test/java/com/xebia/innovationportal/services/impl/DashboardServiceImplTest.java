
package com.xebia.innovationportal.services.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.xebia.innovationportal.entities.Authority;
import com.xebia.innovationportal.entities.Category;
import com.xebia.innovationportal.entities.Idea;
import com.xebia.innovationportal.entities.IdeaLikeDetail;
import com.xebia.innovationportal.entities.IdeaStatus;
import com.xebia.innovationportal.entities.User;
import com.xebia.innovationportal.enums.Role;
import com.xebia.innovationportal.repositories.IdeaRepository;

@RunWith(PowerMockRunner.class)
@SpringBootTest
public class DashboardServiceImplTest {

	@InjectMocks
	private DashboardServiceImpl dashBoardService;

	@Mock
	private IdeaRepository ideaRepository;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void should_get_top_contributor() {
		// Mockito.when(ideaRepository.findByideaStatusNotIn())

	}

	@Test
	public void testGetTrendingIdeas() {

		Idea idea = new Idea();
		idea.setId(1);

		List<Idea> ideas = Arrays.asList(idea);
		when(ideaRepository.findIdeaByLikesCount()).thenReturn(ideas);
		assertEquals(dashBoardService.getTrendingIdeas(), ideas);
	}

	@Test
	public void testGetTopContributor() {
		Idea idea = new Idea();
		idea.setAssignTo("uma");

		Category category = new Category();
		category.setCategoryDescription("des");
		category.setCategoryName("category name");

		idea.setCategory(category);
		idea.setCreationTime(LocalDateTime.now());
		idea.setId(1);
		idea.setIdeaDescription("idea description");
		IdeaStatus status = new IdeaStatus();
		status.setStatus("true");
		status.setStatusDescription("des");
		idea.setIdeaStatus(status);
		idea.setLikeCount(2);

		IdeaLikeDetail likeIdea = new IdeaLikeDetail();
		likeIdea.setIdea(idea);
		User user = new User();
		user.setName("Vishal");
		user.setDisplayName("Vishal yadav");
		user.setEnabled(true);
		user.setDesignation("USA");
		Set<Authority> authorities = new HashSet<>();
		Authority auth = new Authority();
		auth.setRole(Role.ROLE_USER);
		authorities.add(auth);
		user.setAuthorities(authorities);

		likeIdea.setUser(user);

		idea.setUser(user);
		idea.setLikeIdeaList(Arrays.asList(likeIdea));
		idea.setLocation("india");

		when(ideaRepository.findByIdeaStatusIdNotIn(Collections.singletonList(1))).thenReturn(Arrays.asList(idea));

		assertTrue(!dashBoardService.getTopContributor().isEmpty());

	}

}