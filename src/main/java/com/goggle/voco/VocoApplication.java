package com.goggle.voco;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class VocoApplication {

	public static void main(String[] args) {
		SpringApplication.run(VocoApplication.class, args);
	}

}
