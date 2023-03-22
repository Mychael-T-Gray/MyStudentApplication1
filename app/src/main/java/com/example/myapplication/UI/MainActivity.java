package com.example.myapplication.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

import com.example.myapplication.Database.Repository;
import com.example.myapplication.R;
import com.example.myapplication.dao.TermsDao;
import com.example.myapplication.entities.AssessmentsEntity;
import com.example.myapplication.entities.Courses;
import com.example.myapplication.entities.Terms;

public class MainActivity extends AppCompatActivity {
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Course Alert Channel";
            String description = "Channel for course start and end alerts";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("course_alert_channel", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        createNotificationChannel();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.mainScreenTermListButton);
        button.setOnClickListener(view -> {
            Intent intent = new Intent( MainActivity.this, TermList.class);
            startActivity(intent);

        });
    }
}