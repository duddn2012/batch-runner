package com.example.demo.runner;

import static org.junit.jupiter.api.Assertions.*;

import com.example.demo.support.JobContextInitializer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@ContextConfiguration(initializers = JobContextInitializer.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
class BatchJobRunnerTest {

    @Autowired
    private BatchJobRunner batchJobRunner;

    private static final String jobName = "TestJob";

    @BeforeAll
    static void beforeAll() {
        System.setProperty("job.name", jobName);
    }

    @Test
    void runSyncJob() throws Exception {
        String mode = "sync";

        batchJobRunner.executeJob(new String[]{jobName, mode});
    }
}