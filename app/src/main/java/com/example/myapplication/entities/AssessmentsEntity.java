package com.example.myapplication.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ASSESSMENTS")
public class AssessmentsEntity {
    @PrimaryKey(autoGenerate = true)
    private int assessmentId;

    private String assessmentType;
    private String assessmentTitle;
    private String assessmentEndDate;
    private int courseId;

    public AssessmentsEntity( String assessmentType, String assessmentTitle, String assessmentEndDate, int courseId) {

        this.assessmentType = assessmentType;
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

    public String getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
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
