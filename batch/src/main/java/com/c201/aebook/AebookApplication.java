package com.c201.aebook;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.c201.aebook.api.batch.AladinBatchItemReader;

@SpringBootApplication
public class AebookApplication {

	public static void main(String[] args) {
		SpringApplication.run(AebookApplication.class, args);
	}

}
