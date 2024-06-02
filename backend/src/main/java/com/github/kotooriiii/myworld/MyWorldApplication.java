package com.github.kotooriiii.myworld;

import com.github.kotooriiii.myworld.util.SystemEnvironmentUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class MyWorldApplication {

	public static void main(String[] args) {
		SystemEnvironmentUtils.loadEnvironmentFiles();
		SpringApplication.run(MyWorldApplication.class, args);
	}



}
