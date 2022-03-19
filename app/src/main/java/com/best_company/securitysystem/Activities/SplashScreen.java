package com.best_company.securitysystem.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.best_company.securitysystem.Activities.Login.TypeOfLogin;
import com.best_company.securitysystem.R;

public class SplashScreen extends AppCompatActivity {
    public static final String SHARED = "SHARED";
    public static final String LOGGED_IN = "LOGGED_IN";
    boolean loggedIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED, MODE_PRIVATE);
        loggedIn = sharedPreferences.getBoolean(LOGGED_IN,false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i;
                if(loggedIn)
                i = new Intent(SplashScreen.this, MainActivity.class);
                else
                    i = new Intent(SplashScreen.this, TypeOfLogin.class);
                startActivity(i);
                finish();
            }
        }, 1000);
    }
}