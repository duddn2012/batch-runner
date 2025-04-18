package com.example.demo.runner;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class BatchJobRunner implements CommandLineRunner {

    @Autowired
    ApplicationContext context;

    @Autowired
    private JobLauncher jobLauncher;

    @Value("${job.auto.run:true}")
    private boolean autoRun;

    public void executeJob(String... args) throws Exception {
        if(args.length < 0) {
            System.err.println("필수 인자 부족");
            return;
        }

        String jobBeanName = "TestJob"; //args[0];

        Job job = context.getBean(jobBeanName, Job.class);

        JobParameters jobParameters = new JobParametersBuilder()
            .addLong("time", System.currentTimeMillis())
            .toJobParameters();

        JobExecution jobExecution = jobLauncher.run(job, jobParameters);

        System.out.println("Id : " + jobExecution.getJobInstance().getId());
        System.out.println("Job Name : " + jobExecution.getJobInstance().getJobName());
        System.out.println("Exit Status : " + jobExecution.getStatus());
    }

    @Override
    public void run(String... args) {
        if(!autoRun) {
            System.out.println("Job 자동 실행이 비활성화되어 있습니다.");
            return;
        }

        try{
            executeJob(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
