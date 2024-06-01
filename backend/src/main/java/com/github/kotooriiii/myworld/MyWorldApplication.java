package com.github.kotooriiii.myworld;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class MyWorldApplication {

	public static void main(String[] args) {
		loadEnvVariables();
		SpringApplication.run(MyWorldApplication.class, args);
	}

	private static void loadEnvVariables() {
		Dotenv dotenv = Dotenv.configure().filename("./backend/src/main/resources/secrets.env").load();
		// Optionally, you can set a prefix to load only specific variables:
		// Dotenv dotenv = Dotenv.configure().prefix("MY_").load();
		dotenv.entries().forEach(entry -> {
			String key = entry.getKey();
			String value = entry.getValue();
			System.setProperty(key, value);
		});
	}

}
