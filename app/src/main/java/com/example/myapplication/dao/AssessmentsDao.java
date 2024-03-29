package com.example.myapplication.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.entities.AssessmentsEntity;
import com.example.myapplication.entities.AssessmentsEntity;

import java.util.List;

@Dao
public interface AssessmentsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(AssessmentsEntity assessmentsEntity);
    @Update
    void update(AssessmentsEntity assessmentsEntity);
    @Delete
    void delete(AssessmentsEntity assessmentsEntity);
    @Query("SELECT * FROM ASSESSMENTS ORDER BY assessmentId ASC" )
    LiveData<List<AssessmentsEntity>> getAllAssessments();
    @Query("SELECT * FROM assessments WHERE courseId = :courseId")
    LiveData<List<AssessmentsEntity>> getAssessmentsByCourseId(int courseId);
    @Query("SELECT * FROM assessments WHERE assessmentId = :assessmentId")
    LiveData<List<AssessmentsEntity>> getAssessmentsByAssessmentId(int assessmentId);


}
