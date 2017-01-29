package com.crossover.trial.journals.jms;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.crossover.trial.journals.service.LockService;

@Component
public class JournalDigestReceiver {
	
	public static final String DESTINATION = "journal-digest-queue";
	private static final Logger LOGGER = LoggerFactory.getLogger(JournalDigestReceiver.class);

	@Autowired
	private DigestJournalNotificationManager manager;
	
	@Autowired
	private LockService lockService;
	
	@JmsListener(destination = DESTINATION, containerFactory = "myFactory")
	public void receiveMessage(LocalDateTime date) {
		try {
			LOGGER.debug("Received message with request to submit digest for {} date", date);
			
			if(!lockService.lock(MessageFormat.format("digest-journal-on-{0}-date", date.with(LocalTime.MIN)))) {
				LOGGER.info("Digest journal for {} date was already sent", date.with(LocalTime.MIN));
				return;
			}
			
			manager.sendDigest(date);
		} catch (RuntimeException e) {
			LOGGER.error("Failed to generate digest notification", e);
			throw e;
		}
		
	}
}
