package com.example.myapplication.UI;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.myapplication.Database.Repository;
import com.example.myapplication.R;
import com.example.myapplication.entities.Courses;
import com.example.myapplication.entities.Terms;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);
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
        repository=new Repository(getApplication());
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //courseAdapter.setCourses(repository.getmAllCourses());
       /* repository.getmAllCourses().observe(this, new Observer<List<Courses>>() {
            @Override
            public void onChanged(@Nullable final List<Courses> courses) {
                // Update the cached copy of the courses in the adapter.
                courseAdapter.setCourses(courses);
            }
        });*/
        repository.getmCoursesByTermId(termId).observe(this, new Observer<List<Courses>>() {
            @Override
            public void onChanged(@Nullable final List<Courses> courses) {
                // Update the cached copy of the courses in the adapter.
                courseAdapter.setCourses(courses);
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

}