package com.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Back extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back);
    }

    public void deadlift(View view) {
        Intent intent=new Intent(getApplicationContext(), Deadlift.class);
        startActivity(intent);
    }

    public void latpulldown(View view) {
        Intent intent=new Intent(getApplicationContext(), Latpulldown.class);
        startActivity(intent);
    }
}