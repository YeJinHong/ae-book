package com.c201.aebook.api.listener.job;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class BookIntegrationJobListener implements JobExecutionListener {
	
	/**
	 * 잡의 구동 전에 도는 리스너
	 */
	@Override
	public void beforeJob(JobExecution jobExecution) {
		// TODO Auto-generated method stub

	}

	/**
	 * 잡의 구동 이후에 도는 리스너
	 */
	@Override
	public void afterJob(JobExecution jobExecution) {
		// TODO Auto-generated method stub

	}

}
