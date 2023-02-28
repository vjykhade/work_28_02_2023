package com.vjykhade.batchprocessing.spring.web.config;

import com.vjykhade.batchprocessing.spring.web.entity.UserDetails;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Random;


@Configuration
@EnableBatchProcessing
public class BatchConfig {

    private  JobRepository jobRepository;
    private PlatformTransactionManager transactionManagerBatch;

    @Bean
    public ItemReader<UserDetails> reader()
    {
        System.out.println("Coming to Item Reader");
        return new FlatFileItemReader<UserDetails>() {{
            setResource(new FileSystemResource("C:/Users/vjykh/OneDrive/Desktop/MOCK_DATA.csv"));
            setLineMapper(new DefaultLineMapper<UserDetails>() {{
                setLineTokenizer(new DelimitedLineTokenizer() {{
                    setDelimiter(",");
                    setNames("id","fullName","birthDate","city","mobileNo");
                }});
                setFieldSetMapper(new BeanWrapperFieldSetMapper<UserDetails>() {{
                    setTargetType(UserDetails.class);
                }});
            }});
        }};
    }//ItemReader End

    @Bean
    public ItemProcessor<UserDetails, UserDetails> processor() {
        System.out.println("Coming to Item Processor");
        return userDetails -> {
            userDetails.setBatchNo(String.valueOf(new Random().nextInt(9999)));
            return userDetails;
        };
    }//Item Processor End

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Bean
    public ItemWriter<UserDetails> writer(){
        System.out.println("Coming to Item Writer");
        JpaItemWriter<UserDetails> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }//Item Writer End

    @Bean
    public JobExecutionListener listener() {
        System.out.println("Coming to JobExecutionListener");
        return new JobExecutionListener() {
            public void beforeJob(JobExecution je) {
                System.out.println("STARTED WITH =>"+je.getStatus());
            }
            public void afterJob(JobExecution je) {
                System.out.println("FINISHED WITH =>"+je.getStatus());
            }
        };
    }//JobExecutionListener End

    @Bean
    public Step sampleStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("sampleStep", jobRepository)
                .<UserDetails, UserDetails>chunk(10, transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public Job sampleJob(JobRepository jobRepository, Step sampleStep) {
        return new JobBuilder("sampleJob", jobRepository)
                .start(sampleStep)
                .incrementer(new RunIdIncrementer())
                .listener(listener())
                .build();
    }

}//Batch Config End
