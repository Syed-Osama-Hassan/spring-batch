package com.syed.osama.hassan.springbatchintro.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/job")
public class JobController {
    @Autowired
    private final JobLauncher jobLauncher;

    @Qualifier("firstJob")
    @Autowired
    private Job firstJob;

    @Qualifier("chunkJob")
    @Autowired
    private Job secondJob;

    public JobController(JobLauncher jobLauncher) {
        this.jobLauncher = jobLauncher;
    }

    @GetMapping("/start/{jobName}")
    public String startJob(@PathVariable String jobName) {

        Map<String, JobParameter> params = new HashMap<>();
        params.put("Current Time", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(params);

        try {
            if("First Job".equals(jobName)) {
                jobLauncher.run(firstJob, jobParameters);
            } else if("Second Job".equals(jobName)) {
                jobLauncher.run(secondJob, jobParameters);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return "Job Started!";
    }
}
