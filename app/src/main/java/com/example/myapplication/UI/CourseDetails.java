package com.example.myapplication.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Database.Repository;
import com.example.myapplication.R;
import com.example.myapplication.entities.Courses;


public class CourseDetails extends AppCompatActivity {
    private EditText editCourseTitle;
    private EditText editCourseStart;
    private EditText editCourseEnd;
    private EditText editCourseProgress;
    private EditText editInstructorName;
    private EditText editInstructorPhoneNumber;
    private EditText editInstructorEmail;

    private int courseId;
    private int termId;
    private String courseTitle;
    private String courseStart;
    private String courseEnd;
    private String courseProgress;
    private String instructorName;
    private String instructorPhoneNumber;
    private String instructorEmail;

    private Repository repository;

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
        courseStart = getIntent().getStringExtra("courseStartDate");
        courseEnd = getIntent().getStringExtra("courseEndDate");
        courseProgress = getIntent().getStringExtra("courseProgress");
        instructorName = getIntent().getStringExtra("instructorName");
        instructorPhoneNumber = getIntent().getStringExtra("instructorPhoneNumber");
        instructorEmail = getIntent().getStringExtra("instructorEmail");

        editCourseTitle.setText(courseTitle);
        editCourseStart.setText(courseStart);
        editCourseEnd.setText(courseEnd);
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

                if (title.isEmpty() || start.isEmpty() || end.isEmpty() || progress.isEmpty() || name.isEmpty() || phoneNumber.isEmpty() || email.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (courseId == -1) {
                    Courses course = new Courses( title, start, end, progress, name, phoneNumber, email, termId);
                    repository.insert(course);

                } else {
                    Courses course = new Courses(courseId, title, start, end, progress, name, phoneNumber, email, getIntent().getIntExtra("termId", -1));
                    repository.update(course);
                }

                finish();
            }
        });

        Button deleteCourseButton = findViewById(R.id.deleteCourseButton);
        deleteCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Delete the course from the database
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

    }
}




