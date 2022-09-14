package com.syed.osama.hassan.springbatchintro.controller;

import com.syed.osama.hassan.springbatchintro.service.JobService;
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
    private JobService jobService;

    @GetMapping("/start/{jobName}")
    public String startJob(@PathVariable String jobName) {
        jobService.startJob(jobName);
        return "Job Started!";
    }
}
