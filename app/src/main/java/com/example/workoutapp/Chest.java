package com.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Chest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chest);
    }

    public void backbutton(View view) {
        onBackPressed();
    }

    public void benchpress(View view) {
        Intent intent=new Intent(getApplicationContext(), Benchpress.class);
        startActivity(intent);
    }

    public void fly(View view) {
        Intent intent=new Intent(getApplicationContext(), Fly.class);
        startActivity(intent);
    }
}