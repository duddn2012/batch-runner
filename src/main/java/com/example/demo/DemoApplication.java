package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	/**
	 * args: [Job Bean 명시]
	 * ex) TestJob
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(DemoApplication.class);
		app.setWebApplicationType(WebApplicationType.NONE);
		app.run(args);
	}
}
