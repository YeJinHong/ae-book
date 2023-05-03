package com.c201.aebook.api.listener.step;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class BookIntegrationStepListener implements StepExecutionListener {
	
	
	/**
	 * 스텝 구동 전에 도는 리스너
	 */
	@Override
	public void beforeStep(StepExecution stepExecution) {
		log.info("before step execution");
		
	}

	/**
	 * 스텝 구동 후에 도는 리스너
	 */
	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		log.info("after step execution");
		String stepName = stepExecution.getStepName();
		ExitStatus exitStatus = stepExecution.getExitStatus();

		return exitStatus;
	}

}
