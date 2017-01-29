package com.crossover.trial.journals.managers;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import javax.mail.Address;
import javax.mail.internet.MimeMessage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.crossover.trial.journals.Application;
import com.crossover.trial.journals.model.Category;
import com.crossover.trial.journals.model.Journal;
import com.crossover.trial.journals.model.Subscription;
import com.crossover.trial.journals.model.User;
import com.crossover.trial.journals.notifications.JournalNotificationManager;
import com.crossover.trial.journals.repository.JournalRepository;
import com.crossover.trial.journals.repository.SubscriptionRepository;
import com.crossover.trial.journals.service.MailService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class JournalNotificationManagerTest {

	@Autowired
	private JournalNotificationManager manager;
	
	@MockBean
	private SubscriptionRepository subscriptionRepository;
	
	@MockBean
	private JournalRepository journalRepository;
	
	@MockBean
	private MailService mailService;
	
	@Test
	public void test_sendForDate_nothingSent(){
		// prepare
		Category category = mock(Category.class);
		
		Journal journal = mock(Journal.class);
		when(journal.getCategory()).thenReturn(category);
		
		Subscription subscription = mock(Subscription.class);
		
		when(journalRepository.findOne(any())).thenReturn(journal);
		when(subscriptionRepository.findByCategory(category)).thenReturn(Arrays.asList(subscription));
		// act
		manager.notifySubscribers(1L);
		// assert
		verify(mailService).send(any());

	}
	
	@Test
	public void test_composeMail_success() throws Exception{
		// prepare
		MimeMessage mimeMessage = mock(MimeMessage.class);
		User user = mock(User.class);
		when(user.getEmailAddress()).thenReturn("mail@address.com");
		Journal journal = mock(Journal.class);
		// act
		manager.composeMail(journal, user).prepare(mimeMessage);
		// assert
		verify(mimeMessage).setRecipient(any(),any());
		verify(mimeMessage).setFrom(any(Address.class));
	}
}
