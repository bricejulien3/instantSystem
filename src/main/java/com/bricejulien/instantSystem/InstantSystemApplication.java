package com.bricejulien.instantSystem;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@SpringBootApplication
public class InstantSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(InstantSystemApplication.class, args);
	}

	@Bean
	public DataSource dataSource() {
		// Configuration de la base de données H2 en mémoire
		return new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.H2)
				.build();
	}

}
