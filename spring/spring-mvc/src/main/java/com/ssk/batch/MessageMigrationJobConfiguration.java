package com.ssk.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ssk
 * @date 2021/2/23
 */
@Configuration
public class MessageMigrationJobConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job messageMigrationJob() {
        return jobBuilderFactory.get("messageMigrationJob")
                .start(messageMigrationStep())
                .build();
    }


    @Bean
    public Step messageMigrationStep() {
        return stepBuilderFactory.get("messageMigrationStep").tasklet(new Tasklet(){
            @Override
            public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                System.out.println("spring batch job");
                return RepeatStatus.FINISHED;
            }
        }).build();
    }
}
