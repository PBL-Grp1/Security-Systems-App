package com.best_company.securitysystem;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.best_company.securitysystem.databinding.ActivityUserRegisterBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

public class UserRegister extends AppCompatActivity {
    private ActivityUserRegisterBinding binding;
    FirebaseStorage storage;
    FirebaseAuth auth;
    DatabaseReference databaseReference;

    boolean imageAdded = false;
    Uri imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initFirebase();
        setProfilePicture();
        registerUser();
        alreadyHaveAccount();
    }


    void initFirebase() {
        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }
    void registerUser(){
        binding.registerUser.setOnClickListener(view -> createUser());
    }
    void alreadyHaveAccount(){
        binding.alreadyHaveAcc.setOnClickListener(view -> onBackPressed());
    }

    void setProfilePicture() {
        binding.setDP.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, 33);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data.getData() != null) {
            imagePath = data.getData();
            binding.profileImage.setImageURI(imagePath);
            imageAdded = true;
        }else{
            Toast.makeText(this, "Picture not selected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    public void createUser() {
        String emailString = binding.emailId.getText().toString();
        String passwordString = binding.passwordId.getText().toString();
        String nameString = binding.nameId.getText().toString();
        String phoneString = binding.phoneId.getText().toString();

        if (TextUtils.isEmpty(emailString)) {
            binding.emailId.setError("Email Cannot be empty!");
            binding.emailId.requestFocus();
        } else if (TextUtils.isEmpty(passwordString)) {
            binding.passwordId.setError("Password Cannot be empty!");
            binding.passwordId.requestFocus();
        } else if (TextUtils.isEmpty(nameString)) {
            binding.nameId.setError("Name cannot be empty!");
            binding.nameId.requestFocus();
        } else if (TextUtils.isEmpty(phoneString)) {
            binding.phoneId.setError("Phone cannot be empty!");
            binding.phoneId.requestFocus();
        } else if(!imageAdded){
            Toast.makeText(this, "Please add a profile picture", Toast.LENGTH_SHORT).show();
        } else{
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Creating an account, Please wait...");
            progressDialog.show();
            HashMap<String, String> map = new HashMap<>();
            map.put("Name", nameString);
            map.put("Email", emailString);
            map.put("PhoneNo", phoneString);


            auth.createUserWithEmailAndPassword(emailString, passwordString).addOnCompleteListener(task -> {
                progressDialog.dismiss();

                if (task.isSuccessful()) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    assert user != null;
                    String uid = user.getUid();
                    Log.d("RegisterLogs", "The uid is " + uid);
                    Log.d("RegisterLogs", "The map is " + map.toString());
                    databaseReference.child("UserDatabase").child(uid).setValue(map);

                    // to add the image to the storage
                    final StorageReference reference = storage.getReference().child("profile_pictures")
                            .child(auth.getUid());
                    reference.putFile(imagePath);
                    Toast.makeText(getApplicationContext(), "User registered Successfully", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                } else {
                    Toast.makeText(getApplicationContext(), "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            });


        }
    }
}