package com.example.demo.job;

import com.example.demo.common.util.FileWriteUtil;
import com.example.demo.config.JdbcConnectionProvider;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class TestJobConfig {

    private final JdbcConnectionProvider jdbcConnectionProvider;
    private final FileWriteUtil fileWriteUtil;

    @Bean
    public Step DbConnectionTestStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("DbConnectionTestStep", jobRepository)
            .tasklet((stepContribution, chunkContext) -> {
                System.out.println("DbConnectionTestStep 진행 중!");

                Thread.sleep(1000);

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


                System.out.println("DbConnectionTestStep 종료!");

                return RepeatStatus.FINISHED;
            }, platformTransactionManager).build();
    }

    @Bean
    public Step FileTestStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("FileTestStep", jobRepository)
            .tasklet((stepContribution, chunkContext) -> {
                System.out.println("FileTestStep 진행 중!");

                Path path = Paths.get("logs", "app.txt");

                fileWriteUtil.appendWriteLine(path, "test!!");

                System.out.println("FileTestStep 종료!");

                return RepeatStatus.FINISHED;
            }, platformTransactionManager).build();
    }

    @Bean
    public Job TestJob(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new JobBuilder("TestJob", jobRepository)
            .incrementer(new RunIdIncrementer())
            .start(DbConnectionTestStep(jobRepository, platformTransactionManager))
            .next(FileTestStep(jobRepository, platformTransactionManager))
            .build();
    }
}
