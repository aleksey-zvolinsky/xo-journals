package com.crossover.trial.journals.service;

import org.springframework.mail.javamail.MimeMessagePreparator;

/**
 * 
 * Takes responsibility of sending email from the system
 * 
 * @author aleksey.zvolinsky
 *
 */
public interface MailService {
	
	void send(MimeMessagePreparator preparator);
}
