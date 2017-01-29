package com.crossover.trial.journals.notifications;

import java.text.MessageFormat;
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
import com.crossover.trial.journals.model.Subscription;
import com.crossover.trial.journals.model.User;
import com.crossover.trial.journals.repository.JournalRepository;
import com.crossover.trial.journals.repository.SubscriptionRepository;
import com.crossover.trial.journals.service.MailService;

@Component
public class JournalNotificationManager {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JournalNotificationManager.class);

	@Autowired
	private MailService mailService;
	
	@Autowired
	private SubscriptionRepository subscriptionRepository;
	
	@Autowired
	private JournalRepository journalRepository;
	
	public void notifySubscribers(Long journalId) {
		
		Journal journal = journalRepository.findOne(journalId);
		
		List<Subscription> subscriptions = subscriptionRepository.findByCategory(journal.getCategory());
		
		LOGGER.debug("Sending notifications about new {} journal to {} subscribers ", journal, subscriptions.size());
		
		for(Subscription subscription: subscriptions) {
			
			MimeMessagePreparator mailPreparator = composeMail(journal, subscription.getUser());
			
			mailService.send(mailPreparator);
		}
	}

	public MimeMessagePreparator composeMail(Journal journal, User user) {
		return new MimeMessagePreparator() {

            public void prepare(MimeMessage mimeMessage) throws Exception {

                mimeMessage.setRecipient(Message.RecipientType.TO,
                        new InternetAddress(user.getEmailAddress()));
                mimeMessage.setFrom(new InternetAddress("no-reply@example.com"));
                mimeMessage.setText(MessageFormat.format("Dear {0}, \n New {1} journal was published. Please visit our web site to read it", user.getLoginName(), journal.getName()));
            }
        };
	}
}
