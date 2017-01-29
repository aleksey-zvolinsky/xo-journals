package com.crossover.trial.journals.repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.crossover.trial.journals.model.Journal;
import com.crossover.trial.journals.model.Publisher;

public interface JournalRepository extends CrudRepository<Journal, Long> {

    Collection<Journal> findByPublisher(Publisher publisher);

    List<Journal> findByCategoryIdIn(List<Long> ids);

    List<Journal> findByPublishDateBetween(LocalDateTime beginDate, LocalDateTime endDate);
}
