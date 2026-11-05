package com.learn.client_service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClientServiceApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ClientServiceApplication.class, args);
	}

	// Runs once, right after the Spring context has started
	@Override
	public void run(String... args) {
		System.out.println("hello world");
	}
}
