package com.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Abs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abs);
    }

    public void backbutton(View view) {
        onBackPressed();
    }

    public void crunches(View view) {
        Intent intent=new Intent(getApplicationContext(), Crunches.class);
        startActivity(intent);
    }

    public void plank(View view) {
        Intent intent=new Intent(getApplicationContext(), Plank.class);
        startActivity(intent);
    }
}