package com.example.myapplication.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;


import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TermDetails extends AppCompatActivity {
EditText editTermTitle;
EditText editTermStart;
EditText editTermEnd;
String termTitle;
String termStart;
String termEnd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);
        editTermTitle = findViewById(R.id.termDetailsTerTitelEditText);
        editTermStart = findViewById(R.id.termDetailsTermStartDateEditText);
        editTermEnd = findViewById(R.id.termDetailsTermEndDateEditText);

        termTitle = getIntent().getStringExtra("termTitle");
        termStart = getIntent().getStringExtra("termStart");
        termEnd = getIntent().getStringExtra("termEnd");

        editTermTitle.setText(termTitle);
        editTermStart.setText(termStart);
        editTermEnd.setText(termEnd);


    }

}