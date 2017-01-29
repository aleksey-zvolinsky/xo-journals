package com.crossover.trial.journals.rest;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.crossover.trial.journals.Application;
import com.crossover.trial.journals.model.User;
import com.crossover.trial.journals.repository.CategoryRepository;
import com.crossover.trial.journals.repository.UserRepository;
import com.crossover.trial.journals.service.CurrentUser;
import com.crossover.trial.journals.service.JournalService;
import com.crossover.trial.journals.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class JournalRestServiceTest {
	
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private UserRepository userRepository;
	
	@MockBean
	private JournalService journalService;
	
	@SpyBean
	private UserService userService;
	

	@Before
	public void setup() throws Exception {
		this.mockMvc = webAppContextSetup(webApplicationContext)
	            .apply(springSecurity())  
				.build();
	}
	
	@Test
	public void test_browse() throws Exception {
		User user = userRepository.findOne(1L);
		CurrentUser authUser = mock(CurrentUser.class);
		when(authUser.getUser()).thenReturn(user);
		
		mockMvc.perform(get("/rest/journals").with(user(authUser)))
			.andExpect(status().isOk());
		
		verify(journalService).listAll(any());
	}
	
	@Test
	public void test_browsepublished() throws Exception {
		User user = userRepository.findOne(1L);
		CurrentUser authUser = mock(CurrentUser.class);
		when(authUser.getUser()).thenReturn(user);
		
		mockMvc.perform(get("/rest/journals/published").with(user(authUser)))
			.andExpect(status().isOk());
		
		verify(journalService).publisherList(any());
	}
	
	@Test
	public void test_unpublish() throws Exception {
		User user = userRepository.findOne(1L);
		CurrentUser authUser = mock(CurrentUser.class);
		when(authUser.getUser()).thenReturn(user);
		
		mockMvc.perform(delete("/rest/journals/unPublish/22").with(user(authUser)))
			.andExpect(status().isOk());
		
		verify(journalService).unPublish(any(), any());
	}
	
	@Test
	public void test_subscriptions() throws Exception {
		User user = userRepository.findOne(3L);
		CurrentUser authUser = mock(CurrentUser.class);
		when(authUser.getUser()).thenReturn(user);
		when(authUser.getId()).thenReturn(user.getId());
		
		mockMvc.perform(get("/rest/journals/subscriptions").with(user(authUser)))
			.andExpect(status().isOk());
	}
	
	@Test
	public void test_subscribe() throws Exception {
		User user = userRepository.findOne(3L);
		CurrentUser authUser = mock(CurrentUser.class);
		when(authUser.getUser()).thenReturn(user);
		
		mockMvc.perform(post("/rest/journals/subscribe/1").with(user(authUser)))
			.andExpect(status().isOk());
		
		verify(userService).subscribe(any(), any());
	}
}
