package com.example.myapplication.Database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.myapplication.dao.AssessmentsDao;
import com.example.myapplication.dao.CoursesDao;
import com.example.myapplication.dao.TermsDao;
import com.example.myapplication.entities.AssessmentsEntity;
import com.example.myapplication.entities.Courses;
import com.example.myapplication.entities.Terms;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private final TermsDao mTermsDao;
    private final CoursesDao mCoursesDao;
    private final AssessmentsDao mAssessmentsDao;


    private List<Terms> mAllTerms;
    private List<Courses> mAllCourses;
    private List<AssessmentsEntity> mAllAssessments;

    public static int NUMBER_OF_THREADS = 4;
    static final ExecutorService dataBaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application) {
        ScheduleDatabaseBuilder db = ScheduleDatabaseBuilder.getDatabase(application);
        mTermsDao = db.termsDao();
        mCoursesDao = db.coursesDao();
        mAssessmentsDao = db.assessmentsDao();
    }


    public LiveData<List<Terms>> getmAllTerms() {
        return mTermsDao.getAllTerms();
    }

    public void insert(Terms terms) {
        dataBaseExecutor.execute(() -> {
            mTermsDao.insert(terms);
        });
    }

    public void update(Terms terms) {
        dataBaseExecutor.execute(() -> {
            mTermsDao.update(terms);
        });
    }

    public void delete(Terms terms) {
        dataBaseExecutor.execute(() -> {
            mTermsDao.delete(terms);
        });
    }


    public LiveData<List<Courses>> getmAllCourses() {
        return mCoursesDao.getAllCourses();
    }

    public LiveData<List<Courses>> getmCoursesByTermId(int termId) {
        return mCoursesDao.getCoursesByTermId(termId);
    }

    public void insert(Courses courses) {
        dataBaseExecutor.execute(() -> {
            mCoursesDao.insert(courses);
        });
    }

    public void update(Courses courses) {
        dataBaseExecutor.execute(() -> {
            mCoursesDao.update(courses);
        });
    }

    public void delete(Courses courses) {
        dataBaseExecutor.execute(() -> {
            mCoursesDao.delete(courses);
        });

    }


    public LiveData<List<AssessmentsEntity>> getmAllAssessments() {
        return mAssessmentsDao.getAllAssessments();

    }

    public void insert(AssessmentsEntity assessmentsEntity) {
        dataBaseExecutor.execute(() -> {
            mAssessmentsDao.insert(assessmentsEntity);
        });
    }

    public void update(AssessmentsEntity assessmentsEntity) {
        dataBaseExecutor.execute(() -> {
            mAssessmentsDao.update(assessmentsEntity);
        });
    }

    public void delete(AssessmentsEntity assessmentsEntity) {
        dataBaseExecutor.execute(() -> {
            mAssessmentsDao.delete(assessmentsEntity);
        });
    }
}