package com.mafia.application.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


//@ComponentScan({"com.mafia.application.controller", "com.mafia.application.service.PlayerService"})
@ComponentScan(basePackages = {"com.mafia.application"})
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
