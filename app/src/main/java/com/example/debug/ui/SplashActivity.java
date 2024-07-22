package com.example.debug.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.debug.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Create an intent to navigate to LoginActivity
        Intent intent = new Intent(this, LoginActivity.class);

        // Start LoginActivity after a delay
        start(intent);
    }

    private void start(Intent intent) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(intent);
                finish(); // Finish SplashActivity so that it's not on the back stack
            }
        }, 1000); // Delay for 1 second (1000 milliseconds)
    }
}