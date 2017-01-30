package com.crossover.trial.journals.service;

public interface LockService {

	boolean lock(String key);

	void unlock(String key);

}