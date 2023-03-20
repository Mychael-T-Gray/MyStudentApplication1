package com.example.myapplication.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Database.Repository;
import com.example.myapplication.R;
import com.example.myapplication.entities.AssessmentsEntity;

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

    }


}