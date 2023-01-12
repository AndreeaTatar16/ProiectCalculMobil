package com.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Legs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legs);
    }

    public void backbutton(View view) {
        onBackPressed();
    }

    public void squats(View view) {
        Intent intent=new Intent(getApplicationContext(), Squats.class);
        startActivity(intent);
    }

    public void lunges(View view) {
        Intent intent=new Intent(getApplicationContext(), Lunges.class);
        startActivity(intent);
    }
}