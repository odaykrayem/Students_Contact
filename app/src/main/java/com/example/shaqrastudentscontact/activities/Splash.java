package com.example.shaqrastudentscontact.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shaqrastudentscontact.R;


public class Splash extends AppCompatActivity {

    public static final int TIME_TO_START = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {

                nextPhase();
            }
        }, TIME_TO_START);

    }

    private void nextPhase() {
        startActivity(new Intent(this, Login.class));
        finish();
    }
}