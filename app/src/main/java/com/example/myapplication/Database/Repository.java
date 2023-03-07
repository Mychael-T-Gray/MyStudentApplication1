package com.example.myapplication.Database;

import android.app.Application;

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

    public List<Terms> getmAllTerms() {
        dataBaseExecutor.execute(() -> {
            mAllTerms = mTermsDao.getAllTerms();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllTerms;
    }

    public void insert(Terms terms) {
        dataBaseExecutor.execute(() -> {
            mAllTerms = mTermsDao.getAllTerms();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Terms terms) {
        dataBaseExecutor.execute(() -> {
            mAllTerms = mTermsDao.getAllTerms();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Terms terms) {
        dataBaseExecutor.execute(() -> {
            mAllTerms = mTermsDao.getAllTerms();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<Courses> getmAllCourses() {
        dataBaseExecutor.execute(() -> {
            mAllCourses = mCoursesDao.getAllCourses();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllCourses;
    }

    public void insert(Courses courses) {
        dataBaseExecutor.execute(() -> {
            mAllCourses = mCoursesDao.getAllCourses();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Courses courses) {
        dataBaseExecutor.execute(() -> {
            mAllCourses = mCoursesDao.getAllCourses();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Courses courses) {
        dataBaseExecutor.execute(() -> {
            mAllCourses = mCoursesDao.getAllCourses();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


        public List<AssessmentsEntity> getmAllAssessments() {
            dataBaseExecutor.execute(() -> {
                mAllAssessments = mAssessmentsDao.getAllAssessments();
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return mAllAssessments;
        }

    public void insert(AssessmentsEntity assessmentsEntity) {
        dataBaseExecutor.execute(() -> {
            mAllAssessments = mAssessmentsDao.getAllAssessments();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(AssessmentsEntity assessmentsEntity) {
        dataBaseExecutor.execute(() -> {
            mAllAssessments = mAssessmentsDao.getAllAssessments();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(AssessmentsEntity assessmentsEntity) {
        dataBaseExecutor.execute(() -> {
            mAllAssessments = mAssessmentsDao.getAllAssessments();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
