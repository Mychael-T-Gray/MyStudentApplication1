package com.example.myapplication.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.myapplication.dao.AssessmentsDao;
import com.example.myapplication.dao.CourseNotesDao;
import com.example.myapplication.dao.CoursesDao;
import com.example.myapplication.dao.TermsDao;
import com.example.myapplication.entities.AssessmentsEntity;
import com.example.myapplication.entities.CourseNotes;
import com.example.myapplication.entities.Courses;
import com.example.myapplication.entities.Terms;


@Database( entities = {AssessmentsEntity.class, Courses.class, Terms.class, CourseNotes.class}, version = 5, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class ScheduleDatabaseBuilder extends RoomDatabase {
    public abstract TermsDao termsDao();
    public abstract CoursesDao coursesDao();
    public abstract AssessmentsDao assessmentsDao();
    public abstract CourseNotesDao courseNotesDao();

    private static volatile ScheduleDatabaseBuilder INSTANCE;

    public static ScheduleDatabaseBuilder getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (ScheduleDatabaseBuilder.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ScheduleDatabaseBuilder.class, "StudentScheduleDataBase")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }


        }
        return INSTANCE;
    }

}
