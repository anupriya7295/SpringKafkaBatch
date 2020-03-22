package com.bk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BkApplication {

	public static void main(String[] args) {
		SpringApplication.run(BkApplication.class, args);
	}

}
