package com.crossover.trial.journals.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService{

	private static final Logger LOGGER = LoggerFactory.getLogger(MailServiceImpl.class);
	private static final int SECOND = 1000;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Override
	@Retryable(maxAttempts = 10, backoff = @Backoff(multiplier = 2, delay = SECOND * 10))
	public void send(MimeMessagePreparator preparator) {
		LOGGER.debug("Sending mail message");
		mailSender.send(preparator);
		LOGGER.debug("Send success");
	}

}
