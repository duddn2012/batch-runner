package com.example.demo.support;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class JobTestExecutionSupport {

    @Autowired
    JobLauncher jobLauncher;

    private JobParameters makeDefaultJobParameters() {
        String expectedDate = LocalDate.now()
            .format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        long currentDateMilliSeconds = System.currentTimeMillis();

        return new JobParametersBuilder()
            .addLong("curr.seconds=", currentDateMilliSeconds)
            .addString("curr.date=", expectedDate)
            .toJobParameters();
    }

    public JobExecution runSyncJob(Job job) throws Exception {
        return jobLauncher.run(job, makeDefaultJobParameters());
    }

    @Async
    public JobExecution runAsyncJob(Job job) throws Exception {
        return jobLauncher.run(job, makeDefaultJobParameters());
    }
}
