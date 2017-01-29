package com.crossover.trial.journals.schedule;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

import com.crossover.trial.journals.jms.JournalDigestReceiver;

public class JournalsDigestTask implements Runnable {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JournalsDigestTask.class);

	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Override
	public void run() {
		try {
			jmsTemplate.convertAndSend(JournalDigestReceiver.DESTINATION, LocalDateTime.now());
		} catch (RuntimeException e) {
			LOGGER.error("Failed to initiate journal digest generation", e);
		}
		
	}

}
