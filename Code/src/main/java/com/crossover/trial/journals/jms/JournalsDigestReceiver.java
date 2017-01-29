package com.crossover.trial.journals.jms;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.crossover.trial.journals.service.LockService;

@Component
public class JournalsDigestReceiver {
	
	public static final String DESTINATION = "journal-digest-queue";
	private static final Logger LOGGER = LoggerFactory.getLogger(JournalsDigestReceiver.class);

	@Autowired
	private JournalsDigestNotificationManager digestManager;
	
	@Autowired
	private LockService lockService;
	
	@JmsListener(destination = DESTINATION, containerFactory = "myFactory")
	public void receiveMessage(Long epochSeconds) {
		try {
			LocalDateTime date = LocalDateTime.ofEpochSecond(epochSeconds, 0, ZoneOffset.UTC);
			LOGGER.debug("Received message with request to submit digest for {} date", date);
			
			if(!lockService.lock(MessageFormat.format("digest-journal-on-{0}-date", date.with(LocalTime.MIN)))) {
				LOGGER.info("Digest journal for {} date was already sent", date.with(LocalTime.MIN));
				return;
			}
			
			digestManager.sendForDate(date);
		} catch (RuntimeException e) {
			LOGGER.error("Failed to generate digest notification", e);
			throw e;
		}
		
	}
}
