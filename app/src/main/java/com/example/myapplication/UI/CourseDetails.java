package com.example.myapplication.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Database.Repository;
import com.example.myapplication.R;
import com.example.myapplication.entities.AssessmentsEntity;
import com.example.myapplication.entities.CourseNotes;
import com.example.myapplication.entities.Courses;

import java.util.ArrayList;
import java.util.Arrays;
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


    private int courseId;
    private int termId;
    private int assessmentId;
    private String courseTitle;
    private String courseStart;
    private String courseEnd;
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
                List<String> allowedProgressValues = Arrays.asList("in progress", "completed", "dropped", "plan to take");

                if (title.isEmpty() || start.isEmpty() || end.isEmpty() || progress.isEmpty() || name.isEmpty() || phoneNumber.isEmpty() || email.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!allowedProgressValues.contains(progress.toLowerCase())) {
                    Toast.makeText(getApplicationContext(), "Please enter one of these options (in progress, completed, dropped, plan to take)", Toast.LENGTH_SHORT).show();
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


        RecyclerView assessmentRecyclerView = findViewById(R.id.assessmentRecycleView);
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this, new AssessmentAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(AssessmentsEntity assessment) {
                Intent intent= new Intent(CourseDetails.this, AssessmentDetails.class);
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
        Button assessmentsButton= findViewById(R.id.goToAssesments);
        assessmentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CourseDetails.this, AssessmentDetails.class);
                intent.putExtra("courseId", courseId);
                startActivity(intent);
            }
        });
    }
}







