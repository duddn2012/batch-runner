package com.example.demo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	@Autowired
	ApplicationContext context;

	@Autowired
	private JobLauncher jobLauncher;

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

	@Override
	public void run(String... args) throws Exception {
		if(args.length < 0) {
			System.err.println("필수 인자 부족");
			System.exit(1);
		}

		String jobBeanName = "TestJob"; //args[0];

		Job job = (Job)context.getBean(jobBeanName, Job.class);

		JobParameters jobParameters = new JobParametersBuilder()
			.addLong("time", System.currentTimeMillis())
			.toJobParameters();

		JobExecution jobExecution = jobLauncher.run(job, jobParameters);

		System.out.println("Id : " + jobExecution.getJobInstance().getId());
		System.out.println("Job Name : " + jobExecution.getJobInstance().getJobName());
		System.out.println("Exit Status : " + jobExecution.getStatus());

	}
}
