package com.c201.aebook.api.listener.job;

import org.springframework.batch.core.BatchStatus;
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
		log.info("before job execution = " + jobExecution);
	}

	/**
	 * 잡의 구동 이후에 도는 리스너
	 */
	@Override
	public void afterJob(JobExecution jobExecution) {
		log.info("after job execution = " + jobExecution);

		if(jobExecution.getStatus() == BatchStatus.COMPLETED){
			//job success
			log.info("job success => " + jobExecution.getJobInstance().getJobName());
		}else{
			//job failure
			log.info("job failed =>" + jobExecution.getJobInstance().getJobName());
		}
	}

}
