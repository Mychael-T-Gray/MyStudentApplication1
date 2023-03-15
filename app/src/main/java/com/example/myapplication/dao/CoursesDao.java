package com.example.myapplication.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.entities.Courses;

import com.example.myapplication.entities.Terms;

import java.util.List;

@Dao
public interface CoursesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Courses courses);
    @Update
    void update(Courses courses);
    @Delete
    void delete(Courses courses);
    @Query("SELECT * FROM COURSES ORDER BY courseId ASC" )
   // List<Courses> getAllCourses();
    LiveData<List<Courses>> getAllCourses();
    @Query("SELECT * FROM courses WHERE termId = :termId ORDER BY courseId ASC")
    LiveData<List<Courses>> getCoursesByTermId(int termId);


}
