package com.best_company.securitysystem;

import static com.best_company.securitysystem.TypeOfRegister.LOGIN_TITLE;
import static com.best_company.securitysystem.TypeOfRegister.LOGIN_IMAGE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    Button login;
    TextView forgotPW, signUp,loginTitleText;
    EditText emailET,passwordET;
    ImageView info,loginLogo;

    String typeOfLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        buttonClicks();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void buttonClicks(){
        login.setOnClickListener(view->{
            loginUser();
        });
        signUp.setOnClickListener(view -> {
            startActivity(new Intent(this,RegisterActivity.class));
        });
        forgotPW.setOnClickListener(view -> {
            startActivity(new Intent(this,ForgotPasswordActivity.class));
        });

        info.setOnClickListener(view -> {
            startActivity(new Intent(this,ContactUsActivity.class));
        });
    }

    private void init(){
        login = findViewById(R.id.login);
        signUp = findViewById(R.id.signup);

        forgotPW = findViewById(R.id.forgot_password);

        emailET = findViewById(R.id.email_id);
        passwordET = findViewById(R.id.password);

        info = findViewById(R.id.info);


        //set the title for the login page passed from the intent
        loginTitleText = findViewById(R.id.login_type_text);
        typeOfLogin = getIntent().getStringExtra(LOGIN_TITLE);
        loginTitleText.setText(typeOfLogin);

        //set up the login image also passed in the intent and the tint to app_blue color
        loginLogo = findViewById(R.id.login_logo_img);
        int imageId = getIntent().getIntExtra(LOGIN_IMAGE, R.drawable.default_user);
        loginLogo.setImageDrawable(ContextCompat.getDrawable(this, imageId));
        loginLogo.setColorFilter(ContextCompat.getColor(this, R.color.app_blue), android.graphics.PorterDuff.Mode.SRC_IN);
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