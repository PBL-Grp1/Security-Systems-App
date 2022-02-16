package com.best_company.securitysystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    EditText emailET,passwordET,nameET,phoneET;
    Button registerButton;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        register();
    }

    public void init(){
        emailET = findViewById(R.id.email_id);
        passwordET = findViewById(R.id.password);
        nameET = findViewById(R.id.name_id);
        phoneET = findViewById(R.id.phone_id);
        registerButton = findViewById(R.id.register);

        auth = FirebaseAuth.getInstance();
    }

    public void register(){
        registerButton.setOnClickListener(view -> {
            createUser();
        });
    }

    public void createUser(){
        String emailString = emailET.getText().toString();
        String passwordString = passwordET.getText().toString();
        String nameString = nameET.getText().toString();
        String phoneString = phoneET.getText().toString();

        if(TextUtils.isEmpty(emailString)){
            emailET.setError("Email Cannot be empty!");
            emailET.requestFocus();
        }else if(TextUtils.isEmpty(passwordString)){
            passwordET.setError("Password Cannot be empty!");
            passwordET.requestFocus();
        }else if(TextUtils.isEmpty(nameString)){
            nameET.setError("Name cannot be empty!");
            nameET.requestFocus();
        }else if(TextUtils.isEmpty(phoneString)){
            phoneET.setError("Phone cannot be empty!");
            phoneET.requestFocus();
        }else{
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Creating an account, Please wait...");
            progressDialog.show();
            HashMap<String, String> map = new HashMap<>();
            map.put("Name",nameString);
            map.put("Email",emailString);
            map.put("PhoneNo",phoneString);

            auth.createUserWithEmailAndPassword(emailString,passwordString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressDialog.dismiss();

                    if(task.isSuccessful()) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        assert user != null;
                        String uid = user.getUid();
                        FirebaseDatabase.getInstance().getReference().child("UserDatabase").child(uid).setValue(map);
                        Toast.makeText(getApplicationContext(), "User registered Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    }else{
                        Toast.makeText(RegisterActivity.this, "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }
    }

}