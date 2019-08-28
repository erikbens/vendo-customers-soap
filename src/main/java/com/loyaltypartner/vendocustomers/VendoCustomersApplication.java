package com.loyaltypartner.vendocustomers;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class VendoCustomersApplication {
	
	public static void main(String args[]) {
		SpringApplication.run(VendoCustomersApplication.class);
	}

	@Bean
	public CommandLineRunner run() throws Exception {
		return args -> {
		};
	}

}
