package com.example.myapplication.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Database.Repository;
import com.example.myapplication.R;
import com.example.myapplication.entities.AssessmentsEntity;
import com.example.myapplication.entities.Courses;

import java.util.ArrayList;
import java.util.List;

public class AssessmentDetails extends AppCompatActivity {
    private EditText assesmentEndDate;
    private EditText assesmentType;
    private EditText assesmentTitle;

    private int courseId;
    private int assessmentId;
    private String assessmentTitle;
    private String assessmentType;
    private String assessmentEndDate;

    private Repository repository;
    AssessmentsEntity assessmentsEntity;
    AssessmentAdapter assessmentAdapter;
    private List<AssessmentsEntity> mAssessments = new ArrayList<>();

    public AssessmentDetails() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);

        assesmentEndDate = findViewById(R.id.assesmentEndDate);
        assesmentType = findViewById(R.id.assesmentType);
        assesmentTitle = findViewById(R.id.assesmentTitle);

        courseId = getIntent().getIntExtra("courseId", -1);
        assessmentId = getIntent().getIntExtra("assessmentId", -1);
        assessmentTitle = getIntent().getStringExtra("assessmentTitle");
        assessmentType = getIntent().getStringExtra("assessmentType");
        assessmentEndDate = getIntent().getStringExtra("assessmentEndDate");
        Log.d("AssessmentDetails", "courseId: " + courseId + ", assessmentId: " + assessmentId);


        assesmentTitle.setText(assessmentTitle);
        assesmentType.setText(assessmentType);
        assesmentEndDate.setText(assessmentEndDate);
        repository = new Repository(getApplication());

        Button saveAssessmentButton = findViewById(R.id.saveAssessmentButton);
        saveAssessmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = assesmentTitle.getText().toString().trim();
                String type = assesmentType.getText().toString().trim();
                String endDate = assesmentEndDate.getText().toString().trim();

                if (title.isEmpty() || type.isEmpty() || endDate.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (assessmentId == -1) {
                    AssessmentsEntity assessment = new AssessmentsEntity(type, title, endDate, courseId);
                    repository.insert(assessment);
                } else {
                    AssessmentsEntity assessment = new AssessmentsEntity(type, title, endDate, courseId);
                    assessment.setAssessmentId(assessmentId);
                    repository.update(assessment);
                }

                finish();
            }
        });
        Button deleteAssessmentsButton = findViewById(R.id.deleteAssessentsButton);
        deleteAssessmentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                repository.getAssessmentsByAssessmentId(assessmentId).observe(AssessmentDetails.this, new Observer<List<AssessmentsEntity>>() {
                    @Override
                    public void onChanged(List<AssessmentsEntity> assessmentsEntities) {
                        if (assessmentsEntities != null && !assessmentsEntities.isEmpty()) {
                            AssessmentsEntity assessmentToDelete = assessmentsEntities.get(0);
                            repository.delete(assessmentToDelete);
                            finish();
                        }
                    }
                });
            }
        });


    }


}