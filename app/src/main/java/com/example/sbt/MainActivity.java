package com.example.sbt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {
    // Splash screen timer
    private static final int SPLASH_TIME_OUT = 3000; // 3 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Delayed execution of the next activity using a Handler
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start the next activity
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);

                // Close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}