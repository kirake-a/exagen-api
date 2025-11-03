package com.lisoft.exagen;

import org.springframework.boot.SpringApplication;

public class TestExagenApplication {

	public static void main(String[] args) {
		SpringApplication.from(ExagenApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
