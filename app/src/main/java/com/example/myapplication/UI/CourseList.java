package com.example.myapplication.UI;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.Database.Repository;
import com.example.myapplication.R;
import com.example.myapplication.entities.Courses;
import com.example.myapplication.entities.Terms;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class CourseList extends AppCompatActivity {
    int termId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list); // Set the content view

        termId = getIntent().getIntExtra("termId", -1); // Retrieve the termId from the intent

        RecyclerView recyclerView = findViewById(R.id.courseRecyclerViewList);
        Repository repository = new Repository(getApplication());
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        repository.getmCoursesByTermId(termId).observe(this, new Observer<List<Courses>>() {
            @Override
            public void onChanged(@Nullable final List<Courses> courses) {
                // Update the cached copy of the courses in the adapter.
                courseAdapter.setCourses(courses);
            }
        });
    }
}