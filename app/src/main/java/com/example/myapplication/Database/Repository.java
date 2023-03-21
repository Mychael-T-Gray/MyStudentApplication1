package com.example.myapplication.Database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.myapplication.dao.AssessmentsDao;
import com.example.myapplication.dao.CourseNotesDao;
import com.example.myapplication.dao.CoursesDao;
import com.example.myapplication.dao.TermsDao;
import com.example.myapplication.entities.AssessmentsEntity;
import com.example.myapplication.entities.Courses;
import com.example.myapplication.entities.Terms;
import com.example.myapplication.entities.CourseNotes;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private final TermsDao mTermsDao;
    private final CoursesDao mCoursesDao;
    private final AssessmentsDao mAssessmentsDao;
    private final CourseNotesDao mCourseNotesDao;


    private List<Terms> mAllTerms;
    private List<Courses> mAllCourses;
    private List<AssessmentsEntity> mAllAssessments;
    private List<CourseNotes> mAllCourseNotes;

    public static int NUMBER_OF_THREADS = 4;
    static final ExecutorService dataBaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application) {
        ScheduleDatabaseBuilder db = ScheduleDatabaseBuilder.getDatabase(application);
        mTermsDao = db.termsDao();
        mCoursesDao = db.coursesDao();
        mAssessmentsDao = db.assessmentsDao();
        mCourseNotesDao = db.courseNotesDao();
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

    public LiveData<Courses> getCourseById(int courseId) {
        return mCoursesDao.getCourseById(courseId);
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
    public LiveData<List<AssessmentsEntity>> getAssessmentsByCourseId(int courseId) {
        return mAssessmentsDao.getAssessmentsByCourseId(courseId);
    }
    public LiveData<List<AssessmentsEntity>> getAssessmentsByAssessmentId(int assessmentId) {
        return mAssessmentsDao.getAssessmentsByAssessmentId(assessmentId);
    }
    public void insert(AssessmentsEntity assessmentsEntity) {
        dataBaseExecutor.execute(() -> {
            mAssessmentsDao.insert(assessmentsEntity);
            Log.d("Repository", "Assessment inserted: " + assessmentsEntity.getAssessmentId());
        });
    }

    public void update(AssessmentsEntity assessmentsEntity) {
        dataBaseExecutor.execute(() -> {
            mAssessmentsDao.update(assessmentsEntity);
            Log.d("Repository", "Assessment updated: " + assessmentsEntity.getAssessmentId());
        });
    }

    public void delete(AssessmentsEntity assessmentsEntity) {
        dataBaseExecutor.execute(() -> {
            mAssessmentsDao.delete(assessmentsEntity);
        });
    }

        public LiveData<List<CourseNotes>> getCourseNotesByCourseId(int courseId) {
            return mCourseNotesDao.getCourseNotesByCourseId(courseId);
        }

        public void insert(CourseNotes note) {
            dataBaseExecutor.execute(() -> {
                mCourseNotesDao.insert(note);
            });
        }

        public void update(CourseNotes note) {
            dataBaseExecutor.execute(() -> {
                mCourseNotesDao.update(note);
            });
        }

        public void delete(CourseNotes note) {
            dataBaseExecutor.execute(() -> {
                mCourseNotesDao.delete(note);
            });
    }
}