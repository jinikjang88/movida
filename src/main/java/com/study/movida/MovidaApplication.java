package com.study.movida;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MovidaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovidaApplication.class, args);
	}

}
