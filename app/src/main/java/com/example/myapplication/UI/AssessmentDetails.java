package com.example.myapplication.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Database.Repository;
import com.example.myapplication.R;
import com.example.myapplication.entities.AssessmentsEntity;
import com.example.myapplication.entities.Courses;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AssessmentDetails extends AppCompatActivity {
    private EditText assesmentEndDate;
    private EditText assesmentType;
    private EditText assesmentTitle;


    private int courseId;
    private int assessmentId;
    private String assessmentTitle;
    private String assessmentType;
    private Date assessmentEndDate;
    private Date parsedAssessmentEndDate;

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
        long assessmentEndDateAsLong = getIntent().getLongExtra("assessmentEndDate", -1);
        if(assessmentId != -1){
            assessmentEndDate = new Date( assessmentEndDateAsLong);
        }
        Log.d("AssessmentDetails", "courseId: " + courseId + ", assessmentId: " + assessmentId);

        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH-mm", Locale.getDefault());

        assesmentTitle.setText(assessmentTitle);
        assesmentType.setText(assessmentType);
        if (assessmentEndDate != null){
            String assessmentEndDateFormatted = dateFormatter.format(assessmentEndDate);
            assesmentEndDate.setText(assessmentEndDateFormatted);
        }

        repository = new Repository(getApplication());




        Button saveAssessmentButton = findViewById(R.id.saveAssessmentButton);
        saveAssessmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = assesmentTitle.getText().toString().trim();
                String type = assesmentType.getText().toString().trim();
                String endDate = assesmentEndDate.getText().toString().trim();
                List<String> allowedTypeValues = Arrays.asList("performance",  "objective" );
                if (title.isEmpty() || type.isEmpty() || endDate.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!allowedTypeValues.contains(type.toLowerCase())) {
                    Toast.makeText(getApplicationContext(), "Please enter one of these options (performance,objective )", Toast.LENGTH_SHORT).show();
                    return;
                }
                try{
                    parsedAssessmentEndDate = dateFormatter.parse(endDate);
                } catch (ParseException e){
                    Toast.makeText(getApplicationContext(), "Invalid date format. Please use the format: yyyy-MM-dd HH-mm", Toast.LENGTH_SHORT).show();
                }

                if(parsedAssessmentEndDate == null){
                    Toast.makeText(getApplicationContext(), "Invalid date format. Please use the format: yyyy-MM-dd HH-mm", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (assessmentId == -1) {
                    AssessmentsEntity assessment = new AssessmentsEntity(type, title,  parsedAssessmentEndDate, courseId);
                    repository.insert(assessment);
                } else {
                    AssessmentsEntity assessment = new AssessmentsEntity(type, title,  parsedAssessmentEndDate, courseId);
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
        Button setAssessmentAlertButton = findViewById(R.id.setAssessmentAlertButton);
        setAssessmentAlertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText editAssessmentTitle = findViewById(R.id.assesmentTitle);
                EditText editAssessmentEnd = findViewById(R.id.assesmentEndDate);

                String updatedAssessmentTitle = editAssessmentTitle.getText().toString().trim();
                String updatedAssessmentEndDate = editAssessmentEnd.getText().toString().trim();

                assessmentTitle = updatedAssessmentTitle;
                try {
                   parsedAssessmentEndDate = dateFormatter.parse(updatedAssessmentEndDate);

                } catch (ParseException e) {
                    Toast.makeText(getApplicationContext(), "Invalid date format. Please use the format: yyyy-MM-dd HH-mm", Toast.LENGTH_SHORT).show();
                    return;
                }
                setAssessmentAlert(assessmentId, updatedAssessmentTitle, parsedAssessmentEndDate);
                Toast.makeText(getApplicationContext(), "Alert set for end of assessment", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void setAssessmentAlert(int assessmentId, String assessmentTitle, Date parsedAssessmentEndDate) {
        if ( parsedAssessmentEndDate != null && assessmentTitle != null) {
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            Intent endIntent = new Intent(this, AssessmentAlertReceiver.class);
            endIntent.putExtra("assessmentId", assessmentId);
            endIntent.putExtra("assessmentTitle", assessmentTitle);
            endIntent.putExtra("assessmentAlertType", "Assessment End");
            PendingIntent endPendingIntent = PendingIntent.getBroadcast(this, assessmentId, endIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

            if (alarmManager != null) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, parsedAssessmentEndDate.getTime(), endPendingIntent);
            }
        } else {
            Toast.makeText(getApplicationContext(), "Please set the end date for the assessment", Toast.LENGTH_SHORT).show();
        }
    }


}