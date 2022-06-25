package com.example.siliconlab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            public void run() {
                startActivity(intent);
                finish();
            }

        }, 1000);

    }
}