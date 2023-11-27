package com.luheresbar.daily;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories // Para usar los spring data repositories
@EnableJpaAuditing
public class DailyExpensesApplication {

	public static void main(String[] args) {
		SpringApplication.run(DailyExpensesApplication.class, args);
	}

}
