package com.crossover.trial.journals.config;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import com.crossover.trial.journals.schedule.JournalsDigestTask;

@Configuration
public class SchedulerConfig {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SchedulerConfig.class);

	@Value("${journals.dailyCronExpression:0 0 * * * *}")
	private String dailyCronExpression;
	
	@Autowired
	private TaskScheduler scheduler;
	
	@Autowired
	private JournalsDigestTask task;

	@PostConstruct
	public void startScheduler() {
		scheduler.schedule(task, new CronTrigger(dailyCronExpression));
		LOGGER.info("Initialized scheduler for running Journal Digest task using '{}' crontab expression", dailyCronExpression);
	}
	
	@Bean 
	@Singleton
	public TaskScheduler taskScheduler() {
		return new ConcurrentTaskScheduler();
	}
	
}
