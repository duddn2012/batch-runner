package com.example.demo.runner;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = "spring.main.web-application-type=none")
@ActiveProfiles("test")
class BatchJobRunnerTest {

    @Autowired
    private BatchJobRunner batchJobRunner;

    @ParameterizedTest
    @ValueSource(strings = {"TestJob"})
    void runSyncJob(String jobBeanName) throws Exception {

        if(jobBeanName.isEmpty()) return;

        String mode = "sync";

        batchJobRunner.executeJob(new String[]{jobBeanName, mode});
    }

    @ParameterizedTest
    @ValueSource(strings = {""})
    void runAsyncJob(String jobBeanName) throws Exception {

        if(jobBeanName.isEmpty()) return;

        String mode = "async";

        batchJobRunner.executeJob(new String[]{jobBeanName, mode});
    }
}