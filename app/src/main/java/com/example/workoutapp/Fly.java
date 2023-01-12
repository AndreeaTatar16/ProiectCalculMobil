package com.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Fly extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fly);
        TextView flyTV = findViewById(R.id.flyTV);
        flyTV.setText(R.string.fly);
        Button startexercise = findViewById(R.id.startexercise);
    }

    public void backbutton(View view) {
        onBackPressed();
    }

    public void startexercise(View view) {
        Intent intent = new Intent(getApplicationContext(), StartExercise.class);
        startActivity(intent);
    }
}