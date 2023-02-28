package com.vjykhade.batchprocessing.spring.web.runner;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
@Component
public class UserDetailsRunner implements CommandLineRunner{

    @Autowired
    private JobLauncher launcher;

    @Autowired
    private Job job;

//    @Scheduled(cron = "*/10 * * * * *")
//    public void perform() throws Exception
//    {
//        JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters();
//        JobExecution jobExecution = launcher.run(job,jobParameters);
//    }
    public void run(String... args) throws Exception {
        System.out.println("ABOUT TO START JOB EXECUTION...");
        JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters();
        JobExecution jobExecution = launcher.run(job,jobParameters);
        System.out.println("Job Execution Status: "+jobExecution.getStatus());

    }
}
