package com.crossover.trial.journals.jms;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import com.crossover.trial.journals.model.Journal;
import com.crossover.trial.journals.model.User;
import com.crossover.trial.journals.repository.JournalRepository;
import com.crossover.trial.journals.repository.UserRepository;
import com.crossover.trial.journals.service.MailService;

@Component
public class JournalsDigestNotificationManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(JournalsDigestNotificationManager.class);

	@Autowired
	private MailService mailService;

	@Autowired
	private JournalRepository journalRepository;

	@Autowired
	private UserRepository userRepository;
	
	public void sendForDate(LocalDateTime date) {
		
		LocalDateTime startOfDay = date.with(LocalTime.MIN);
		LocalDateTime endOfDay = date.with(LocalTime.MAX);

		List<Journal> newJournals = journalRepository.findByPublishDateBetween(
				Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant()), 
				Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant()));
		
		if (newJournals.isEmpty()) {
			LOGGER.info("No new journals between {} and {}", startOfDay, endOfDay);
			return;
		}

		String digest = composeDigestText(newJournals);

		List<User> users = userRepository.findAll();
		
		LOGGER.info("Sending digest to {} users", users.size());
		
		for (User user : users) {
			mailService.send(composeMail(digest, user));
		}
	}

	private String composeDigestText(List<Journal> newJournals) {
		StringBuilder builder = new StringBuilder();
		newJournals.forEach(j -> builder.append(j.getName()).append("\n"));
		return builder.toString();
	}

	private MimeMessagePreparator composeMail(String digestText, User user) {
		return new MimeMessagePreparator() {

            public void prepare(MimeMessage mimeMessage) throws Exception {
				mimeMessage.setRecipient(Message.RecipientType.TO,
		                new InternetAddress(user.getEmailAddress()));
		        mimeMessage.setFrom(new InternetAddress("no-reply@example.com"));
		        mimeMessage.setText(MessageFormat.format("Dear {0}, new arrivals on our web site:\n {1}", user.getLoginName(), digestText));
            }
		};
	}

}
