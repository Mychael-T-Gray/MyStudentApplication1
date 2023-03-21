package com.example.myapplication.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "course_notes")

public class CourseNotes {
    @PrimaryKey(autoGenerate = true)
    private int noteId;

    private int courseId;

    @NonNull
    private String noteText;

    public CourseNotes(int courseId, @NonNull String noteText) {
        this.courseId = courseId;
        this.noteText = noteText;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    @NonNull
    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(@NonNull String noteText) {
        this.noteText = noteText;
    }
}

