package com.example.myapplication.dao;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.example.myapplication.entities.CourseNotes;

import java.util.List;
@Dao
public interface CourseNotesDao {
   @Insert
long insert(CourseNotes courseNote);

    @Update
    void update(CourseNotes courseNote);

    @Delete
    void delete(CourseNotes courseNote);

    @Query("SELECT * FROM course_notes WHERE courseId = :courseId")
    LiveData<List<CourseNotes>> getCourseNotesByCourseId(int courseId);

}
