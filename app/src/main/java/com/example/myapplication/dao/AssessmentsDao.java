package com.example.myapplication.dao;

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
    List<AssessmentsEntity> getAllAssessments();
}
