package com.example.myapplication.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ASSESSMENTS")
public class AssessmentsEntity {
    @PrimaryKey(autoGenerate = true)
    private int assessmentId;
    private String performanceAssessment;
    private String objectiveAssessment;
    private String assessmentTitle;
    private String assessmentEndDate;
    private int courseId;

    public AssessmentsEntity(int assessmentId, String performanceAssessment, String objectiveAssessment, String assessmentTitle, String assessmentEndDate, int courseId) {
        this.assessmentId = assessmentId;
        this.performanceAssessment = performanceAssessment;
        this.objectiveAssessment = objectiveAssessment;
        this.assessmentTitle = assessmentTitle;
        this.assessmentEndDate = assessmentEndDate;
        this.courseId = courseId;
    }

    public AssessmentsEntity() {
    }

    public int getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(int assessmentId) {
        this.assessmentId = assessmentId;
    }

    public String getPerformanceAssessment() {
        return performanceAssessment;
    }

    public void setPerformanceAssessment(String performanceAssessment) {
        this.performanceAssessment = performanceAssessment;
    }

    public String getObjectiveAssessment() {
        return objectiveAssessment;
    }

    public void setObjectiveAssessment(String objectiveAssessment) {
        this.objectiveAssessment = objectiveAssessment;
    }

    public String getAssessmentTitle() {
        return assessmentTitle;
    }

    public void setAssessmentTitle(String assessmentTitle) {
        this.assessmentTitle = assessmentTitle;
    }

    public String getAssessmentEndDate() {
        return assessmentEndDate;
    }

    public void setAssessmentEndDate(String assessmentEndDate) {
        this.assessmentEndDate = assessmentEndDate;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
