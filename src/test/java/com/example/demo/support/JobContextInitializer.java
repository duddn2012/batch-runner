package com.example.demo.support;

import static com.example.demo.support.JobClassResolver.resolveJobClass;

import com.example.demo.DemoApplication;
import com.example.demo.runner.BatchJobRunner;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.support.GenericApplicationContext;

public class JobContextInitializer implements ApplicationContextInitializer<GenericApplicationContext> {

    @Override
    public void initialize(GenericApplicationContext context) {
        String targetJobName = System.getProperty("job.name");

        if (targetJobName == null) {
            throw new IllegalArgumentException("job.name 시스템 속성을 설정해야 합니다.");
        }

        String basePackage = DemoApplication.class.getPackageName();

        Class<?> jobConfigClass = resolveJobClass(targetJobName, basePackage);

        context.registerBean(jobConfigClass);
        context.registerBean(BatchJobRunner.class);
    }
}
