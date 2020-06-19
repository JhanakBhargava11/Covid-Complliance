
package com.xebia.innovationportal.services.impl;

import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

import com.xebia.innovationportal.entities.Idea;
import com.xebia.innovationportal.entities.IdeaStatus;
import com.xebia.innovationportal.exceptions.GenericException;
import com.xebia.innovationportal.repositories.CategoryRepository;
import com.xebia.innovationportal.repositories.IdeaRepository;
import com.xebia.innovationportal.repositories.IdeaStatusHistoryRepository;
import com.xebia.innovationportal.repositories.IdeaStatusRespository;
import com.xebia.innovationportal.repositories.SubCategoryRepository;
import com.xebia.innovationportal.repositories.UserRepository;

@RunWith(PowerMockRunner.class)
@SpringBootTest
public class IdeaServiceImplTest {

    @Mock
    private IdeaRepository ideaRepository;

    @InjectMocks
    private IdeaServiceImpl ideaService;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private SubCategoryRepository subCategoryRepository;

    @Mock
    private IdeaStatusRespository ideaStatusRespository;

    @Mock
    private IdeaStatusHistoryRepository ideaStatusHistoryRepository;

    @Mock
    private UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Rule
    private ExpectedException expectedExceptionRule = ExpectedException.none();

    /*
     * @Test public void testSaveIdea() {
     *
     * IdeaDataRequest request=new IdeaDataRequest();
     * request.setAzureUrl("www.azure.com"); request.setCategoryId(12);
     * request.setFileName("resume"); request.setId(1);
     * request.setIdeaDescription("file upload"); request.setIdeaStatusId(13);
     * request.setSubCategoryId(134); request.setTitle("resume");
     *
     * ideaService.saveIdea(request);
     *
     * }
     */

    @Test
    public void testfindAllIdeaByStatus() {

        Idea idea = new Idea();
        idea.setIdeaDescription("idea description");
        idea.setTitle("idea");
        List<Idea> ideas = Arrays.asList(idea);

        IdeaStatus ideaStatus = new IdeaStatus();
        ideaStatus.setStatus("true");
        ideaStatus.setStatusDescription("good idea");

        when(ideaStatusRespository.findByStatus("true")).thenReturn(ideaStatus);
        when(ideaRepository.findByIdeaStatusOrderBySubmissionDateDesc(ideaStatus)).thenReturn(ideas);
        ideaService.findAllIdeaByStatus("true");

    }

//	@Test
//	public void testGetIdeaByUserId() {
//		
//		User user = new User();
//		user.setName("Vishal");
//		user.setDisplayName("Vishal yadav");
//		user.setEnabled(true);
//		Set<Authority> authorities = new HashSet<>();
//		Authority auth = new Authority();
//		auth.setRole(Role.ROLE_USER);
//		authorities.add(auth);
//		user.setAuthorities(authorities);
//		
//		Idea idea = new Idea();
//		idea.setIdeaDescription("idea description");
//		idea.setTitle("idea");
//		idea.setAzureUrl("www.azure.com");
//		idea.setFileName("resume");
//		idea.setId(12);
//		List<Idea> ideas=Arrays.asList(idea);
//		
//		
//		when(userRepository.findById(1l)).thenReturn(Optional.of(user));
//		when(ideaRepository.findByUser(user)).thenReturn(ideas);
//		
//		assertTrue(ideaService.getIdeaByUserId(1l).size()>0);
//		
//	}

//	@Test
//	public void testGetIdeaByUserIdThrowsGenericException() {
//		
//		expectedExceptionRule.expect(GenericException.class);		
//		when(userRepository.findById(1l)).thenReturn(Optional.empty());
//		ideaService.getIdeaByUserId(1l);
//		
//	}

    @Test
    public void testfindIdeaById() {

        expectedExceptionRule.expect(GenericException.class);
        when(userRepository.findById(1l)).thenReturn(Optional.empty());
        ideaService.findIdeaById(12);

    }

}
