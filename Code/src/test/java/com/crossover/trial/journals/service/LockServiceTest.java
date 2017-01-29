package com.crossover.trial.journals.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.crossover.trial.journals.Application;
import com.crossover.trial.journals.service.LockService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class LockServiceTest {

	@Autowired
	private LockService service;
	
	@Test
	public void test_lock() throws Exception {
		assertThat(service.lock("key1"), is(true));
		assertThat(service.lock("key1"), is(false));
		assertThat(service.lock("key1"), is(false));

		assertThat(service.lock("key2"), is(true));
		assertThat(service.lock("key2"), is(false));
		assertThat(service.lock("key2"), is(false));
	}
}
