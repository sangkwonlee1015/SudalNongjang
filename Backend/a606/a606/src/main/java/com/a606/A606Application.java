package com.a606;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class A606Application {

	public static void main(String[] args) {
		SpringApplication.run(A606Application.class, args);
	}

}
