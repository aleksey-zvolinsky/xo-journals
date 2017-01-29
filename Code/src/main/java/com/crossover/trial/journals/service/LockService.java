package com.crossover.trial.journals.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.crossover.trial.journals.model.Lock;
import com.crossover.trial.journals.repository.LockRepository;


@Service
public class LockService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LockService.class);
	
	@Autowired
	private LockRepository lockRepository;
	
	public boolean lock(String key){
		try {
			Lock lock = new Lock();
			lock.setKey(key);
			lockRepository.save(lock);
			return true;
		} catch (DataIntegrityViolationException e) {
			LOGGER.info("Already locked {} key", key);
			LOGGER.debug("", e);
			return false;
		} catch (RuntimeException e) {
			throw e;
		}
	}
}
