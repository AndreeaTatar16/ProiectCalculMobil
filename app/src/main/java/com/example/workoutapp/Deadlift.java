package com.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Deadlift extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deadlift);
        TextView indicationsDLTV = findViewById(R.id.indicationsDLTV);
        indicationsDLTV.setText(R.string.deadlift1);
    }

    public void backbutton(View view) {
        onBackPressed();
    }

    public void startexercise(View view) {
        Intent intent = new Intent(getApplicationContext(), StartExercise.class);
        startActivity(intent);
    }
}