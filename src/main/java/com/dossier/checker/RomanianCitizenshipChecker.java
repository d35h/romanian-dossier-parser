package com.dossier.checker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
@EnableScheduling
public class RomanianCitizenshipChecker {
	public static void main(String[] args) {
		ApiContextInitializer.init();
		SpringApplication.run(RomanianCitizenshipChecker.class, args);
	}
}
