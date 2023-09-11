package com.konasl.batchjob1;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class Batchjob1Application {

    public static void main(String[] args) {
        SpringApplication.run(Batchjob1Application.class, args);
    }
}
