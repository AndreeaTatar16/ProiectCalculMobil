package com.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Latpulldown extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latpulldown);
        TextView indicationsTv = findViewById(R.id.indicationsTV);
        indicationsTv.setText(R.string.latpulldown1);
    }

    public void backbutton(View view) {
        onBackPressed();
    }

    public void startexercise(View view) {
        Intent intent = new Intent(getApplicationContext(), StartExercise.class);
        startActivity(intent);
    }
}