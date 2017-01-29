package com.crossover.trial.journals.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.crossover.trial.journals.jms.NewJournalReceiver;
import com.crossover.trial.journals.model.Journal;

@Service
public class JournalNotificationServiceImpl implements JournalNotificationService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JournalNotificationServiceImpl.class);
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Override
	public void notifySubscribers(Journal journal) {
		
		LOGGER.debug("Put new journal the queue processor");
		jmsTemplate.convertAndSend(NewJournalReceiver.DESTINATION, journal.getId());
	}

}
