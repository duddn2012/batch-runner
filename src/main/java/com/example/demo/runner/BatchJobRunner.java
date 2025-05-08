package com.example.demo.runner;

import org.springframework.batch.core.Job;
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

        String jobBeanName = args[0];
        String mode = args[1];

        Job job = context.getBean(jobBeanName, Job.class);

        JobParameters jobParameters = new JobParametersBuilder()
            .addLong("time", System.currentTimeMillis())
            .toJobParameters();

        if("sync".equalsIgnoreCase(mode)) {
            jobLauncher.run(job, jobParameters);
        } else {
            // run async job
            // TODO: jobLauncher 추상화 필요 @Async 기반 처리
            //jobLauncher.run(job, jobParameters);
        }
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
