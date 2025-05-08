package com.example.demo;

import static com.example.demo.support.JobClassResolver.resolveJobClass;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example.demo.common", "com.example.demo.runner", "com.example.demo.support"})
public class DemoApplication {

	/**
	 * args: [Job Bean 명시]
	 * ex) TestJob
	 * @param args
	 */
	public static void main(String[] args) {
		String targetJobName = args[0];
		String basePackage = DemoApplication.class.getPackageName();

		Class<?> jobConfigClass = resolveJobClass(targetJobName, basePackage);

		SpringApplication app = new SpringApplication(DemoApplication.class, jobConfigClass);
		app.run(args);
	}
}
