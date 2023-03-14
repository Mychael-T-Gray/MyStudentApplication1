package com.example.myapplication.UI;

import androidx.appcompat.app.AppCompatActivity;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);
        RecyclerView termListLRecyclerView = findViewById(R.id.termListRecyclerView);
        final TermAdapter termAdapter = new TermAdapter(this);
        termListLRecyclerView.setAdapter(termAdapter);
        termListLRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        repository = new Repository(getApplication());
        List<Terms> allTerms = repository.getmAllTerms();
        termAdapter.setTerms(allTerms);

        FloatingActionButton fab = findViewById(R.id.termListFloatingActionButton);
        fab.setOnClickListener(view -> {
  // public void onClick(View view)
    Intent intent = new Intent(TermList.this, TermDetails.class);
        startActivity(intent);
    });
    }

    @Override
    protected void onResume(){

        super.onResume();
        List<Terms> allTerms = repository.getmAllTerms();
        RecyclerView recyclerView = findViewById(R.id.termListRecyclerView);
        final TermAdapter termAdapter = new TermAdapter(this);
        recyclerView.setAdapter(termAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        termAdapter.setTerms(allTerms);
    }
}
