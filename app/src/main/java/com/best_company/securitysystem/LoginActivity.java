package com.best_company.securitysystem;

import static com.best_company.securitysystem.TypeOfLogin.LOGIN_TYPE;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    Button login;
    TextView forgotPW, signUp, loginTitleText;
    TextInputEditText emailET, passwordET;
    TextInputLayout emailContainer, passwordContainer;
    ImageView devContact, loginLogo;
    public static final int DEVICE_LOGIN = TypeOfLogin.DEVICE_LOGIN;
    public static final int USER_LOGIN = TypeOfLogin.USER_LOGIN;
    int loginType;

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

    private void buttonClicks() {
        login.setOnClickListener(view -> {
            loginUser();
        });
        signUp.setOnClickListener(view -> {
            Intent intent;
            if(loginType == USER_LOGIN){
                intent = new Intent(this, UserRegister.class);
            }else{
                intent = new Intent(this, CameraRegister.class);
            }
            startActivity(intent);
        });
        forgotPW.setOnClickListener(view -> {
            Intent intent = new Intent(this, ForgotPasswordActivity.class);
            intent.putExtra(LOGIN_TYPE, loginType);
            startActivity(intent);
        });

        devContact.setOnClickListener(view -> {
            startActivity(new Intent(this, ContactUsActivity.class));
        });
    }

    private void init() {
        login = findViewById(R.id.registerUser);
        signUp = findViewById(R.id.alreadyHaveAcc);

        forgotPW = findViewById(R.id.forgot_password);

        emailET = findViewById(R.id.email_id);
        passwordET = findViewById(R.id.password_id);

        emailContainer = findViewById(R.id.email_container);
        passwordContainer = findViewById(R.id.password_container);

        devContact = findViewById(R.id.info);


        loginTitleText = findViewById(R.id.login_type_text);
        loginLogo = findViewById(R.id.login_logo_img);

        //set up the title and image from the int passed from the TypeOfLogin activity
        loginType = getIntent().getIntExtra(LOGIN_TYPE, 1);
        int imageId;
        String title;
        if (loginType == DEVICE_LOGIN) {
            title = "Camera Login";
            imageId = R.drawable.login_camera;
        } else {
            title = "User Login";
            imageId = R.drawable.login_user;
        }
        loginTitleText.setText(title);

        loginLogo.setImageDrawable(ContextCompat.getDrawable(this, imageId));
        loginLogo.setColorFilter(ContextCompat.getColor(this, R.color.app_blue), android.graphics.PorterDuff.Mode.SRC_IN);
    }



    private void loginUser() {
        String emailString = emailET.getText().toString();
        String passwordString = passwordET.getText().toString();
        if (TextUtils.isEmpty(emailString)) {
            emailET.setError("Email cannot be empty");
            emailET.requestFocus();
        } else if (TextUtils.isEmpty(passwordString)) {
            passwordET.setError("Email cannot be empty");
            passwordET.requestFocus();
        } else {
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Signing in, Please wait...");
            progressDialog.show();
            FirebaseAuth.getInstance().signInWithEmailAndPassword(emailString, passwordString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Login Successful!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Login Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}