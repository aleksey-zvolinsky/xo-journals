package com.crossover.trial.journals.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService{

	private static final Logger LOGGER = LoggerFactory.getLogger(MailServiceImpl.class);
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Override
	public void send(MimeMessagePreparator preparator) {
		LOGGER.debug("Sending mail message");
		mailSender.send(preparator);
		LOGGER.debug("Send success");
	}

}
