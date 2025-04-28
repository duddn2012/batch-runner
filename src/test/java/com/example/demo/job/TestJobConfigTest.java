package com.example.demo.job;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.demo.support.DatabaseTestSupport;
import com.example.demo.support.JobTestExecutionSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class TestJobConfigTest {

    @Autowired
    DatabaseTestSupport databaseTestSupport;

    @Autowired
    JobTestExecutionSupport jobExecutor;

    @Autowired
    @Qualifier("TestJob")
    private Job TestJob;

    @BeforeEach
    void setUp() throws Exception {
        databaseTestSupport.executeSql("sql/testcase/TestJobConfig/cleanup_data.sql");
        databaseTestSupport.executeSql("sql/testcase/TestJobConfig/init_data.sql");
    }

    @Test
    void testJob() throws Exception {
        // given

        // when
        JobExecution jobExecution = jobExecutor.runSyncJob(TestJob);

        // then
        assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);
    }
}