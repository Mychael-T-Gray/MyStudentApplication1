package com.example.myapplication.UI;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;


import com.example.myapplication.Database.Repository;
import com.example.myapplication.R;
import com.example.myapplication.entities.Terms;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TermList extends AppCompatActivity {
private Repository repository;
private Terms term;
private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);



        setTitle("Add Term");
        RecyclerView termListLRecyclerView = findViewById(R.id.termListRecyclerView);
        final TermAdapter termAdapter = new TermAdapter(this);
        termListLRecyclerView.setAdapter(termAdapter);
        termListLRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        repository = new Repository(getApplication());


        repository.getmAllTerms().observe(this, new Observer<List<Terms>>() {
            @Override
            public void onChanged(List<Terms> terms) {

                termAdapter.setTerms(terms);
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_term:
                Intent intent = new Intent(TermList.this, TermDetails.class);
                startActivity(intent);
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_term_list, menu);
        return true;
    }
    @Override
    protected void onResume(){

        super.onResume();
    }
}
