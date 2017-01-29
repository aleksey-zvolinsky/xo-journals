package com.crossover.trial.journals.service;

import com.crossover.trial.journals.model.Journal;

public interface JournalNotificationService {

	void notifySubscribers(Journal journal);

}
