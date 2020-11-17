package com.mafia.application.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;


//@ComponentScan({"com.mafia.application.controller", "com.mafia.application.service.PlayerService"})
@ComponentScan(basePackages = {"com.mafia.application"})
@SpringBootApplication
public class DemoApplication extends SpringBootServletInitializer {
	
	@Override
	   protected SpringApplicationBuilder configure (SpringApplicationBuilder builder) {
	       return builder.sources(DemoApplication.class);
	   }

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
