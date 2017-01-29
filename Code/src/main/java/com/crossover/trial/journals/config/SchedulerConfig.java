package com.crossover.trial.journals.config;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;

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

	@Value("${journals.dailyCronExpression:}")
	private String dailyCronExpression = "";
	
	@Autowired
	private TaskScheduler scheduler;
	

	@PostConstruct
	public void startScheduler() {
		scheduler.schedule(new JournalsDigestTask(), new CronTrigger("0 15 * * * *"));
	}
	
	@Bean 
	@Singleton
	public TaskScheduler taskScheduler() {
		return new ConcurrentTaskScheduler();
	}
	
}
