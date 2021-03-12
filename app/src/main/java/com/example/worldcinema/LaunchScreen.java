package com.example.worldcinema;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class LaunchScreen extends AppCompatActivity {
    SharedPreferences prefs = null;
    Handler handler;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_screen);
        handler = new Handler();
        prefs = getSharedPreferences("appRunnable", 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        intent = new Intent(LaunchScreen.this, SignInScreen.class);
        if (prefs.getBoolean("firstRun", true)) {
            intent= new Intent(LaunchScreen.this, SignUpScreen.class);
            prefs.edit().putBoolean("firstRun", false).commit();
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            }
        },3000L);
    }
}