package com.c201.aebook;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.c201.aebook.api.batch.AladinBatchItemReader;


@EnableJpaAuditing
@SpringBootApplication
public class AebookApplication {

	public static void main(String[] args) {
		SpringApplication.run(AebookApplication.class, args);
	}

}
