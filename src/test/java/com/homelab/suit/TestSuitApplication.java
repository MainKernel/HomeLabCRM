package com.homelab.suit;

import org.springframework.boot.SpringApplication;

public class TestSuitApplication {

	public static void main(String[] args) {
		SpringApplication.from(SuitApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
