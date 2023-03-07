package com.example.myapplication.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "COURSES")
public class Courses {
    @PrimaryKey(autoGenerate = true)
    private int courseId;
    private String courseTitle;
    private String courseStartDate;
    private String courseEndDate;
    private String courseProgress;
    private String instructorName;
    private String instructorPhoneNumber;
    private String instructorEmail;
    private int termId;

    public Courses(int courseId, String courseTitle, String courseStartDate, String courseEndDate, String courseProgress, String instructorName, String instructorPhoneNumber, String instructorEmail, int termId) {
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.courseStartDate = courseStartDate;
        this.courseEndDate = courseEndDate;
        this.courseProgress = courseProgress;
        this.instructorName = instructorName;
        this.instructorPhoneNumber = instructorPhoneNumber;
        this.instructorEmail = instructorEmail;
        this.termId = termId;
    }

    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }

    public Courses() {
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseStartDate() {
        return courseStartDate;
    }

    public void setCourseStartDate(String courseStartDate) {
        this.courseStartDate = courseStartDate;
    }

    public String getCourseEndDate() {
        return courseEndDate;
    }

    public void setCourseEndDate(String courseEndDate) {
        this.courseEndDate = courseEndDate;
    }

    public String getCourseProgress() {
        return courseProgress;
    }

    public void setCourseProgress(String courseProgress) {
        this.courseProgress = courseProgress;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getInstructorPhoneNumber() {
        return instructorPhoneNumber;
    }

    public void setInstructorPhoneNumber(String instructorPhoneNumber) {
        this.instructorPhoneNumber = instructorPhoneNumber;
    }

    public String getInstructorEmail() {
        return instructorEmail;
    }

    public void setInstructorEmail(String instructorEmail) {
        this.instructorEmail = instructorEmail;
    }
}