package com.example.myapplication.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.myapplication.Database.Repository;
import com.example.myapplication.R;
import com.example.myapplication.dao.TermsDao;
import com.example.myapplication.entities.AssessmentsEntity;
import com.example.myapplication.entities.Courses;
import com.example.myapplication.entities.Terms;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.mainScreenTermListButton);
       /* Terms terms = new Terms(0,"English","1/11/111","2/2/2222");
        Repository repository = new Repository(getApplication());
        repository.insert(terms);

        Courses courses = new Courses("Math","12/12/1212","13/13/1313", "Completed", "Big Bill", "345-344-3434", "abd@as.com", 1);
        Repository repository1 = new Repository(getApplication());
        repository1.insert(courses);*/

        /*AssessmentsEntity assessmentsEntity = new AssessmentsEntity("objective Assessment", "Finals", "12/12/1212",2);
        Repository respository2 = new Repository(getApplication());
        respository2.insert(assessmentsEntity);*/


        button.setOnClickListener(view -> {
            Intent intent = new Intent( MainActivity.this, TermList.class);
            startActivity(intent);

        });
    }
}