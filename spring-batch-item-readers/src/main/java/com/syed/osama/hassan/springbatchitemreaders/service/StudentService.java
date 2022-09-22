package com.syed.osama.hassan.springbatchitemreaders.service;

import com.syed.osama.hassan.springbatchitemreaders.model.StudentCsv;
import com.syed.osama.hassan.springbatchitemreaders.model.StudentResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.client.RestTemplate;

@Service
public class StudentService {
    List<StudentResponse> students;

    public List<StudentResponse> restCallToGetStudents() {
        RestTemplate restTemplate = new RestTemplate();
        StudentResponse[] studentResponses = restTemplate.getForObject("http://localhost:8081/api/v1/students", StudentResponse[].class);

        students = new ArrayList<>();

        for(StudentResponse s : studentResponses) {
            students.add(s);
        }

        return students;
    }

    public StudentResponse getStudent() {
        if(students == null) {
            restCallToGetStudents();
        }

        if(students != null && !students.isEmpty()) {
            return students.remove(0);
        }

        return null;
    }

    public StudentResponse restCallToCreateStudent(StudentCsv studentCsv) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject("http://localhost:8081/api/v1/create",
                studentCsv, StudentResponse.class);
    }
}
