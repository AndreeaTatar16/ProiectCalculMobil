package com.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Cardio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardio);
    }

    public void backbutton(View view) {
        onBackPressed();
    }


    public void treadmill(View view) {
        Intent intent=new Intent(getApplicationContext(), Treadmill.class);
        startActivity(intent);
    }

    public void elliptical(View view) {
        Intent intent=new Intent(getApplicationContext(), Elliptical.class);
        startActivity(intent);
    }

}