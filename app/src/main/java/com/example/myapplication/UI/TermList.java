package com.example.myapplication.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.Database.Repository;
import com.example.myapplication.R;
import com.example.myapplication.entities.Terms;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TermList extends AppCompatActivity {
private Repository repository;
private Terms term;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);
        RecyclerView termListLRecyclerView = findViewById(R.id.termListRecyclerView);
        final TermAdapter termAdapter = new TermAdapter(this);
        termListLRecyclerView.setAdapter(termAdapter);
        termListLRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        repository = new Repository(getApplication());


        repository.getmAllTerms().observe(this, new Observer<List<Terms>>() {
            @Override
            public void onChanged(List<Terms> terms) {
                // Update the RecyclerView's data when the terms list changes
                termAdapter.setTerms(terms);
            }
        });



        FloatingActionButton fab = findViewById(R.id.termListFloatingActionButton);
        fab.setOnClickListener(view -> {

            Intent intent = new Intent(TermList.this, TermDetails.class);
            startActivity(intent);
    });
    }

    @Override
    protected void onResume(){

        super.onResume();
    }
}
