package com.crossover.trial.journals.dto;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.crossover.trial.journals.model.Category;

public class SubscriptionDTOTest {

	@Test
	public void testEquals() throws Exception {
		Category category = new Category();
		category.setId(1L);
		category.setName("name");
		
		SubscriptionDTO o1 = new SubscriptionDTO(category);
		o1.setActive(true);
		o1.setId(1L);
		o1.setName("o1");
		
		SubscriptionDTO o2 = new SubscriptionDTO(category);
		o2.setActive(true);
		o2.setId(1L);
		o2.setName("o1");
		
		assertEquals(o1, o2);
		
		o2.setActive(false);
		o2.setName("o2");
		
		assertEquals(o1, o2);
		
	}
}
