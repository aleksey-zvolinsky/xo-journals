package com.crossover.trial.journals.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.crossover.trial.journals.Application;
import com.crossover.trial.journals.model.User;
import com.crossover.trial.journals.repository.UserRepository;
import com.crossover.trial.journals.service.CurrentUser;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class JournalControllerTest {
	
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private UserRepository userRepository;
	

	@Before
	public void setup() throws Exception {
		this.mockMvc = webAppContextSetup(webApplicationContext)
	            .apply(springSecurity())  
				.build();
	}
	

	@Test(expected = FileNotFoundException.class)
	public void test_render_failonfilereasd() throws Exception {
		// prepare
		User user = userRepository.findOne(3L);
		CurrentUser authUser = mock(CurrentUser.class);
		when(authUser.getUser()).thenReturn(user);
		
		// act and assert
		mockMvc.perform(get("/view/1").with(user(authUser)));

	}
	
	public void test_render_return_404() throws Exception {
		// prepare
		User user = userRepository.findOne(3L);
		CurrentUser authUser = mock(CurrentUser.class);
		when(authUser.getUser()).thenReturn(user);
		
		// act and assert
		mockMvc.perform(get("/view/2").with(user(authUser)))
			.andExpect(status().isNotFound());

	}

}
