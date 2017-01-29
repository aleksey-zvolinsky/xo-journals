package com.crossover.trial.journals.repository;

import org.springframework.data.repository.CrudRepository;

import com.crossover.trial.journals.model.Lock;

public interface LockRepository extends CrudRepository<Lock, Long> {

}
