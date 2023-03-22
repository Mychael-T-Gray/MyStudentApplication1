package com.example.myapplication.UI;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.example.myapplication.Database.Repository;
import com.example.myapplication.R;
import com.example.myapplication.entities.Courses;
import com.example.myapplication.entities.Terms;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class TermDetails extends AppCompatActivity {

    EditText editTermTitle;
    EditText editTermStart;
    EditText editTermEnd;
    String termTitle;
    String termStart;
    String termEnd;
    int termId;
    Terms terms;
    Repository repository;
    private CourseAdapter mAdapter;
    private List<Courses> mCourses = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);
        createDeleteTermButton();
        editTermTitle = findViewById(R.id.termDetailsTerTitelEditText);
        editTermStart = findViewById(R.id.termDetailsTermStartDateEditText);
        editTermEnd = findViewById(R.id.termDetailsTermEndDateEditText);

        termId = getIntent().getIntExtra("termId",-1);
        termTitle = getIntent().getStringExtra("termTitle");
        termStart = getIntent().getStringExtra("termStart");
        termEnd = getIntent().getStringExtra("termEnd");

        editTermTitle.setText(termTitle);
        editTermStart.setText(termStart);
        editTermEnd.setText(termEnd);
        repository=new Repository(getApplication());

        RecyclerView recyclerView = findViewById(R.id.courseRecycleView);
        final CourseAdapter courseAdapter = new CourseAdapter(this, new CourseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Courses course) {
                Intent intent = new Intent(TermDetails.this, CourseDetails.class);
                intent.putExtra("courseId", course.getCourseId());
                intent.putExtra("courseTitle", course.getCourseTitle());
                //intent.putExtra("courseStartDate", course.getCourseStartDate());
                long courseStartDateAsLong = course.getCourseStartDate().getTime();
                intent.putExtra("courseStartDate", courseStartDateAsLong);
               // intent.putExtra("courseEndDate", course.getCourseEndDate());

                long courseEndDateAsLong = course.getCourseEndDate().getTime();
                intent.putExtra("courseEndDate", courseEndDateAsLong);
                intent.putExtra("courseProgress", course.getCourseProgress());
                intent.putExtra("instructorName", course.getInstructorName());
                intent.putExtra("instructorPhoneNumber", course.getInstructorPhoneNumber());
                intent.putExtra("instructorEmail", course.getInstructorEmail());
                intent.putExtra("termId", termId);
                startActivity(intent);
            }
        });


        if(termId == -1){
            courseAdapter.setCourses(new ArrayList<>());
        }

        else {
            recyclerView.setAdapter(courseAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            repository.getmCoursesByTermId(termId).observe(this, new Observer<List<Courses>>() {
                @Override
                public void onChanged(@Nullable final List<Courses> courses) {
                    // Update the cached copy of the courses in the adapter.
                    if (courses != null) {
                        mCourses = courses;
                        courseAdapter.setCourses(courses);
                        mAdapter = courseAdapter;
                        Log.d("CourseList", "onChanged: courses size = " + courses.size());
                    }
                }

            });
        }
        Button buttonAddCourse = findViewById(R.id.addCourseButton);
        buttonAddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( TermDetails.this, CourseDetails.class);
                intent.putExtra("courseId", -1);
                intent.putExtra("termId", termId);
                startActivity(intent);
            }
        });
        Button button = findViewById(R.id.saveTermButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(termId == -1 ){
                    terms= new Terms(0,editTermTitle.getText().toString(),editTermStart.getText().toString(), editTermEnd.getText().toString());
                    repository.insert(terms);
                    //Toast.makeText(this, "Term is saved",Toast.LENGTH_LONG).show();
                }
                else{
                    terms= new Terms(termId,editTermTitle.getText().toString(),editTermStart.getText().toString(), editTermEnd.getText().toString());

                    repository.update(terms);

                    //Toast.makeText(this, "Term is updated",Toast.LENGTH_LONG).show();
                }
                Intent intent = new Intent( TermDetails.this, TermList.class);
                startActivity(intent);
            }

        });

    }
    private void createDeleteTermButton() {

        Button deleteTermButton = new Button(this);
        deleteTermButton.setText("Delete Term");
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        deleteTermButton.setLayoutParams(layoutParams);
        deleteTermButton.setBackgroundColor(Color.BLUE);


        deleteTermButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                repository.getmCoursesByTermId(termId).observe(TermDetails.this, new Observer<List<Courses>>() {
                    @Override
                    public void onChanged(@Nullable final List<Courses> courses) {
                        if (courses != null && courses.size() > 0) {

                            Toast.makeText(TermDetails.this, "Cannot delete term with associated courses.", Toast.LENGTH_SHORT).show();
                        } else {

                            Terms termToDelete = new Terms(termId, termTitle, termStart, termEnd);
                            repository.delete(termToDelete);


                            Intent intent = new Intent(TermDetails.this, TermList.class);
                            startActivity(intent);
                        }
                    }
                });
            }
        });
        LinearLayout buttonContainer = findViewById(R.id.linearLayoutTermDisplayVertical);
        buttonContainer.addView(deleteTermButton);
    }
}
