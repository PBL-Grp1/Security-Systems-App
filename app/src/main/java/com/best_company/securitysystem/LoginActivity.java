package com.best_company.securitysystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    Button login;
    TextView forgotPW, signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

        login.setOnClickListener(view->{
            Toast.makeText(this, "firebase nahi dala ab tak :P", Toast.LENGTH_SHORT).show();
        });
        signUp.setOnClickListener(view -> {
            startActivity(new Intent(this,RegisterActivity.class));
        });
        forgotPW.setOnClickListener(view -> {
            startActivity(new Intent(this,ForgotPasswordActivity.class));

        });

    }

    public void init(){
        login = findViewById(R.id.login);
        forgotPW = findViewById(R.id.forgot_password);
        signUp = findViewById(R.id.signup);
    }
}