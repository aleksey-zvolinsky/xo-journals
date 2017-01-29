package com.crossover.trial.journals.model.listeners;

import javax.inject.Singleton;

import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.persister.entity.EntityPersister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.crossover.trial.journals.model.Journal;
import com.crossover.trial.journals.service.JournalNotificationService;

@Component
@Singleton
public class PostInsertJournalListener implements PostInsertEventListener {
	private static final long serialVersionUID = 7349745018953529209L;

	private static final Logger LOGGER = LoggerFactory.getLogger(PostInsertJournalListener.class);

	@Autowired 
	private JournalNotificationService journalNotificationService;
	
	@Override
	public void onPostInsert(PostInsertEvent event) {
		if(event.getEntity() instanceof Journal) {
			Journal journal = (Journal) event.getEntity();
			LOGGER.debug("Post insert action for {} journal", journal);
			journalNotificationService.notifySubscribers(journal);
		}
	}

	@Override
	public boolean requiresPostCommitHanding(EntityPersister persister) {
		return false;
	}

}
