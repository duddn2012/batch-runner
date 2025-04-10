package com.example.demo.job;

import com.example.demo.config.JdbcConnectionProvider;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableAsync
public class TestJobConfig {

    @Autowired
    private JdbcConnectionProvider jdbcConnectionProvider;

    @Bean
    public Step TestStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("TestStep", jobRepository)
            .tasklet((stepContribution, chunkContext) -> {
                System.out.println("Step 진행 중!");

                try(
                    Connection conn = jdbcConnectionProvider.getConnection();
                    PreparedStatement pstmt = conn.prepareStatement("SELECT ID, NAME FROM RAPEECH_TEST");
                    ResultSet rs = pstmt.executeQuery()
                    ) {
                    while (rs.next()) {
                        Long id = rs.getLong("ID");
                        String name = rs.getString("NAME");
                        System.out.println("ID: " + id + ", NAME: " + name);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException("DB 조회 중 오류 발생", e);
                }

                return RepeatStatus.FINISHED;
            }, platformTransactionManager).build();
    }

    @Bean
    public Job TestJob(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new JobBuilder("TestJob", jobRepository)
            .incrementer(new RunIdIncrementer())
            .start(TestStep(jobRepository, platformTransactionManager))
            .build();
    }
}
