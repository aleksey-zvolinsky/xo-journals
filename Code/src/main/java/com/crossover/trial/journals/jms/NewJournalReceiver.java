package com.crossover.trial.journals.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.crossover.trial.journals.notifications.JournalNotificationManager;

@Component
public class NewJournalReceiver {

	private static final Logger LOGGER = LoggerFactory.getLogger(NewJournalReceiver.class);
	public static final String DESTINATION = "new-journal-queue";
	
	@Autowired 
	private JournalNotificationManager manager;

	@JmsListener(destination = DESTINATION, containerFactory = "myFactory")
	public void receiveMessage(Long journalId) {
		try {
			LOGGER.debug("Received message about new journal creation with {} id", journalId);
			manager.notifySubscribers(journalId);
		} catch (RuntimeException e) {
			LOGGER.error("Failed to process initiate subscriber notification", e);
			throw e;
		}
		
	}

}
