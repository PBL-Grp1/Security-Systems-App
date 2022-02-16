package com.best_company.securitysystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    Button login;
    TextView forgotPW, signUp;
    EditText emailET,passwordET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        login.setOnClickListener(view->{
            loginUser();
        });
        signUp.setOnClickListener(view -> {
            startActivity(new Intent(this,RegisterActivity.class));
        });
        forgotPW.setOnClickListener(view -> {
            startActivity(new Intent(this,ForgotPasswordActivity.class));
        });

    }

    private void init(){
        login = findViewById(R.id.register);
        forgotPW = findViewById(R.id.forgot_password);
        signUp = findViewById(R.id.signup);
        emailET = findViewById(R.id.email_id);
        passwordET = findViewById(R.id.password);
    }

    private void loginUser(){
        String emailString = emailET.getText().toString();
        String passwordString = passwordET.getText().toString();
        if(TextUtils.isEmpty(emailString)){
            emailET.setError("Email cannot be empty");
            emailET.requestFocus();
        }else if(TextUtils.isEmpty(passwordString)){
            passwordET.setError("Email cannot be empty");
            passwordET.requestFocus();
        }else{
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Signing in, Please wait...");
            progressDialog.show();
            FirebaseAuth.getInstance().signInWithEmailAndPassword(emailString,passwordString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressDialog.dismiss();
                    if(task.isSuccessful()){

                        Toast.makeText(getApplicationContext(), "Login Sussesfull!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }else{
                        Toast.makeText(getApplicationContext(), "Login Error: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}