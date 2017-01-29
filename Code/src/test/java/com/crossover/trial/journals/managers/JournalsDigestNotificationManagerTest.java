package com.crossover.trial.journals.managers;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.mail.Address;
import javax.mail.internet.MimeMessage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.crossover.trial.journals.Application;
import com.crossover.trial.journals.model.Journal;
import com.crossover.trial.journals.model.User;
import com.crossover.trial.journals.notifications.JournalsDigestNotificationManager;
import com.crossover.trial.journals.repository.JournalRepository;
import com.crossover.trial.journals.service.MailService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class JournalsDigestNotificationManagerTest {
	
	@MockBean
	private MailService mailService;
	@MockBean
	private JournalRepository journalRepository;

	
	@Autowired
	private JournalsDigestNotificationManager manager;
	
	@Test
	public void test_sendForDate_nothingSent(){
		// prepare
		// act
		manager.sendForDate(LocalDateTime.now());
		// assert
		verify(mailService, times(0)).send(any());
	}
	
	@Test
	public void test_sendForDate_send_success(){
		// prepare
		when(journalRepository.findByPublishDateBetween(any(), any())).thenReturn(mockJournals());
		// act
		manager.sendForDate(LocalDateTime.now());
		// assert
		verify(mailService, atLeastOnce()).send(any());
	}
	
	private List<Journal> mockJournals() {
		List<Journal> journals = new ArrayList<Journal>();
		Journal journal = mock(Journal.class);
		journals.add(journal);
		return journals;
	}
	
	@Test
	public void test_composeMail_success() throws Exception{
		// prepare
		MimeMessage mimeMessage = mock(MimeMessage.class);
		User user = mock(User.class);
		when(user.getEmailAddress()).thenReturn("mail@address.com");
		// act
		manager.composeMail("digest text", user).prepare(mimeMessage);
		// assert
		verify(mimeMessage).setRecipient(any(),any());
		verify(mimeMessage).setFrom(any(Address.class));
	}

}
