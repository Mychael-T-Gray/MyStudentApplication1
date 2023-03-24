package com.example.myapplication.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import com.example.myapplication.Database.Repository;
import com.example.myapplication.R;
import com.example.myapplication.entities.AssessmentsEntity;
import com.example.myapplication.entities.CourseNotes;
import com.example.myapplication.entities.Courses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class CourseDetails extends AppCompatActivity {
    private EditText editCourseTitle;
    private EditText editCourseStart;
    private EditText editCourseEnd;
    private EditText editCourseProgress;
    private EditText editInstructorName;
    private EditText editInstructorPhoneNumber;
    private EditText editInstructorEmail;

    private EditText enterCourseNotes;
    private RecyclerView courseNotesRecyclerView;
    private CourseNotesAdapter courseNotesAdapter;

    private Date parsedStartDate;
    private Date parsedEndDate;
    private int courseId;
    private int termId;
    private int assessmentId;
    private String courseTitle;
    private Date courseStart;
    private Date courseEnd;
    private String courseProgress;
    private String instructorName;
    private String instructorPhoneNumber;
    private String instructorEmail;

    private Repository repository;
    AssessmentsEntity assessmentsEntity;
    AssessmentAdapter mAdapter;
    private List<AssessmentsEntity> mAssessments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        editCourseTitle = findViewById(R.id.courseDetailsCourseTitleEditText);
        editCourseStart = findViewById(R.id.courseDetailsCourseStartDate);
        editCourseEnd = findViewById(R.id.courseDetailsCourseEndDateEditText);
        editCourseProgress = findViewById(R.id.editCourseProgress);
        editInstructorName = findViewById(R.id.termDetailsInstructorName);
        editInstructorPhoneNumber = findViewById(R.id.courseDetailsInstructorPhoneNumberEditText);
        editInstructorEmail = findViewById(R.id.termDetailsInstructorEmail);


        courseId = getIntent().getIntExtra("courseId", -1);
        termId = getIntent().getIntExtra("termId", -1);
        courseTitle = getIntent().getStringExtra("courseTitle");

        long courseStartDateAsLong = getIntent().getLongExtra("courseStartDate", -1);
        if (courseStartDateAsLong != -1) {
            courseStart = new Date(courseStartDateAsLong);
        }

        long courseEndDateAsLong = getIntent().getLongExtra("courseEndDate", -1);
        if (courseEndDateAsLong != -1) {
            courseEnd = new Date(courseEndDateAsLong);
        }
        courseProgress = getIntent().getStringExtra("courseProgress");
        instructorName = getIntent().getStringExtra("instructorName");
        instructorPhoneNumber = getIntent().getStringExtra("instructorPhoneNumber");
        instructorEmail = getIntent().getStringExtra("instructorEmail");

        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH-mm", Locale.getDefault());

        editCourseTitle.setText(courseTitle);

        if (courseStart != null) {
            String courseStartFormatted = dateFormatter.format(courseStart);
            editCourseStart.setText(courseStartFormatted);
        }

        if (courseEnd != null) {
            String courseEndFormatted = dateFormatter.format(courseEnd);
            editCourseEnd.setText(courseEndFormatted);
        }
        editCourseProgress.setText(courseProgress);
        editInstructorName.setText(instructorName);
        editInstructorPhoneNumber.setText(instructorPhoneNumber);
        editInstructorEmail.setText(instructorEmail);

        repository = new Repository(getApplication());


        Button saveCourseButton = findViewById(R.id.saveCourseButton);
        saveCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editCourseTitle.getText().toString().trim();
                String start = editCourseStart.getText().toString().trim();
                String end = editCourseEnd.getText().toString().trim();
                String progress = editCourseProgress.getText().toString().trim();
                String name = editInstructorName.getText().toString().trim();
                String phoneNumber = editInstructorPhoneNumber.getText().toString().trim();
                String email = editInstructorEmail.getText().toString().trim();
                List<String> allowedProgressValues = Arrays.asList("in progress", "completed", "dropped", "plan to take");

                if (title.isEmpty() || start.isEmpty() || end.isEmpty() || progress.isEmpty() || name.isEmpty() || phoneNumber.isEmpty() || email.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!allowedProgressValues.contains(progress.toLowerCase())) {
                    Toast.makeText(getApplicationContext(), "Please enter one of these options (in progress, completed, dropped, plan to take)", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {

                    parsedStartDate = dateFormatter.parse(start);
                    parsedEndDate = dateFormatter.parse(end);
                } catch (ParseException e) {

                    Toast.makeText(getApplicationContext(), "Invalid date format. Please use the format: yyyy-MM-dd HH-mm", Toast.LENGTH_SHORT).show();
                }
                if (parsedStartDate == null || parsedEndDate == null) {
                    Toast.makeText(getApplicationContext(), "Invalid date format. Please use the format: yyyy-MM-dd HH-mm", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (courseId == -1) {
                    Courses course = new Courses(title, parsedStartDate, parsedEndDate, progress, name, phoneNumber, email, termId);
                    repository.insert(course);

                } else {
                    Courses course = new Courses(courseId, title, parsedStartDate, parsedEndDate, progress, name, phoneNumber, email, getIntent().getIntExtra("termId", -1));
                    repository.update(course);
                }

                finish();
            }

        });


        Button deleteCourseButton = findViewById(R.id.deleteCourseButton);
        deleteCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                repository.getCourseById(courseId).observe(CourseDetails.this, new Observer<Courses>() {
                    @Override
                    public void onChanged(Courses course) {
                        if (course != null) {
                            repository.delete(course);
                            finish();
                        }
                    }
                });
            }

        });


        RecyclerView assessmentRecyclerView = findViewById(R.id.assessmentRecycleView);
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this, new AssessmentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(AssessmentsEntity assessment) {
                Intent intent = new Intent(CourseDetails.this, AssessmentDetails.class);
                intent.putExtra("assessmentId", assessment.getAssessmentId());
                intent.putExtra("assessmentType", assessment.getAssessmentType());
                intent.putExtra("assessmentTitle", assessment.getAssessmentTitle());
                intent.putExtra("assessmentEndDate", assessment.getAssessmentEndDate());
                intent.putExtra("courseId", assessment.getCourseId());
                startActivity(intent);
                ;
            }
        });
        assessmentRecyclerView.setAdapter(assessmentAdapter);
        repository.getAssessmentsByCourseId(courseId).observe(this, new Observer<List<AssessmentsEntity>>() {
            @Override
            public void onChanged(List<AssessmentsEntity> assessmentsEntities) {
                if (assessmentsEntities != null) {
                    assessmentAdapter.setAssessments(assessmentsEntities);
                }
            }
        });

        assessmentRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        enterCourseNotes = findViewById(R.id.enterCourseNotes);
        courseNotesRecyclerView = findViewById(R.id.courseNotesRecyclerView);
        courseNotesAdapter = new CourseNotesAdapter(this);
        courseNotesRecyclerView.setAdapter(courseNotesAdapter);
        courseNotesRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        Button addCourseNotes = findViewById(R.id.addCourseNotes);
        addCourseNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String noteText = enterCourseNotes.getText().toString().trim();
                if (noteText.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter a note", Toast.LENGTH_SHORT).show();
                    return;
                }

                CourseNotes note = new CourseNotes(courseId, noteText);
                repository.insert(note);
                enterCourseNotes.setText("");
            }
        });

        if (courseId != -1) {
            repository.getCourseNotesByCourseId(courseId).observe(this, new Observer<List<CourseNotes>>() {
                @Override
                public void onChanged(List<CourseNotes> courseNotes) {
                    courseNotesAdapter.setCourseNotes(courseNotes);
                }
            });
        }


        Button assessmentsButton = findViewById(R.id.goToAssesments);
        assessmentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CourseDetails.this, AssessmentDetails.class);
                intent.putExtra("courseId", courseId);
                startActivity(intent);
            }
        });


        Button setCourseAlertButton = findViewById(R.id.setCourseAlert);
        setCourseAlertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String updatedCourseTitle = editCourseTitle.getText().toString().trim();
                String updatedStartDate = editCourseStart.getText().toString().trim();
                String updatedEndDate = editCourseEnd.getText().toString().trim();

                try {
                    parsedStartDate = dateFormatter.parse(updatedStartDate);
                    parsedEndDate = dateFormatter.parse(updatedEndDate);
                } catch (ParseException e) {
                    Toast.makeText(getApplicationContext(), "Invalid date format. Please use the format: yyyy-MM-dd HH-mm", Toast.LENGTH_SHORT).show();
                    return;
                }

                setCourseAlert(courseId, updatedCourseTitle, parsedStartDate, parsedEndDate);
                Toast.makeText(getApplicationContext(), "Alerts set for Start and End of course", Toast.LENGTH_SHORT).show();
            }
        });
    }


        private void setCourseAlert ( int courseId, String courseTitle, Date parsedStartDate, Date
        parsedEndDate){

            if (parsedStartDate != null && parsedEndDate != null && courseTitle != null) {
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                Intent startIntent = new Intent(this, CourseAlertReceiver.class);
                startIntent.putExtra("courseId", courseId);
                startIntent.putExtra("courseTitle", courseTitle);
                startIntent.putExtra("alertType", "Course Start");
                PendingIntent startPendingIntent = PendingIntent.getBroadcast(this, courseId * 2, startIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

                Intent endIntent = new Intent(this, CourseAlertReceiver.class);
                endIntent.putExtra("courseId", courseId);
                endIntent.putExtra("courseTitle", courseTitle);
                endIntent.putExtra("alertType", "Course End");
                PendingIntent endPendingIntent = PendingIntent.getBroadcast(this, courseId * 2 + 1, endIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

                if (alarmManager != null) {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, parsedStartDate.getTime(), startPendingIntent);
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, parsedEndDate.getTime(), endPendingIntent);
                }
            } else {
                Toast.makeText(getApplicationContext(), "Please set the start and end dates for the course", Toast.LENGTH_SHORT).show();
            }

        }
    }






