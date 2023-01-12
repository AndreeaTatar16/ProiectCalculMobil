package com.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Biceps extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biceps);
    }

    public void backbutton(View view) {
        onBackPressed();
    }

    public void hammercurls(View view) {
        Intent intent=new Intent(getApplicationContext(), Hammercurls.class);
        startActivity(intent);
    }

    public void  barbellcurls(View view) {
        Intent intent=new Intent(getApplicationContext(), Barbellcurls.class);
        startActivity(intent);
    }
}