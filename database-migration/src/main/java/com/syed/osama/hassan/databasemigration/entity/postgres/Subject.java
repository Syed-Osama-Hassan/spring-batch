package com.syed.osama.hassan.databasemigration.entity.postgres;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "subjects_learning")
public class Subject {
    @Id
    private Long id;
    @Column(name = "sub_name")
    private String subjectName;
    @Column(name = "student_id")
    private Long studentId;
    @Column(name = "marks_obtained")
    private Long marks;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getMarks() {
        return marks;
    }

    public void setMarks(Long marks) {
        this.marks = marks;
    }
}
