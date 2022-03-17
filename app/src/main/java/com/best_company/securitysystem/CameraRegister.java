package com.best_company.securitysystem;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.best_company.securitysystem.databinding.ActivityCameraRegisterBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;

public class CameraRegister extends AppCompatActivity {

    ActivityCameraRegisterBinding binding;
    FirebaseStorage storage;
    FirebaseAuth auth;
    DatabaseReference databaseReference;
    String locationType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCameraRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initFirebase();
        register();
        spinnerSetup();
    }

    void initFirebase() {
        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    void register() {
        binding.registerUser.setOnClickListener(view -> registerCamera());
    }

    void registerCamera() {
        String emailString = binding.emailId.getText().toString();
        String passwordString = binding.passwordId.getText().toString();
        String nameString = binding.nameId.getText().toString();
        String phoneString = binding.phoneId.getText().toString();
        String locationString = binding.locationId.getText().toString();

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
            binding.phoneId.setError("Phone number cannot be empty!");
            binding.phoneId.requestFocus();
        } else if (TextUtils.isEmpty(locationString)) {
            binding.locationId.setError("Location cannot be empty!");
            binding.locationId.requestFocus();
        } else {
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Creating an account, Please wait...");
            progressDialog.show();
            HashMap<String, String> map = new HashMap<>();
            map.put("DeviceName", nameString);
            map.put("Email", emailString);
            map.put("PhoneNo", phoneString);
            map.put("Location", locationString);
            map.put("LocationType", locationType);

            auth.createUserWithEmailAndPassword(emailString, passwordString).addOnCompleteListener(task -> {

                if (task.isSuccessful()) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    assert user != null;
                    String uid = user.getUid();
                    databaseReference.child("DeviceDatabase").child(uid).setValue(map);

                    Toast.makeText(getApplicationContext(), "Camera registered Successfully", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    onBackPressed();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    void spinnerSetup() {
        String[] types = {"Home", "Office", "School", "College", "Society"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, types);
        binding.locationType.setAdapter(adapter);
        binding.locationType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                locationType = types[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                locationType = types[0];
            }
        });
    }
}