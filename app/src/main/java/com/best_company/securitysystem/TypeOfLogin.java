package com.best_company.securitysystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class TypeOfLogin extends AppCompatActivity {

    ConstraintLayout getStartedButton,cameraCL,userCL;
    ImageView cameraLoginImg,userLoginImg;
    TextView cameraLoginTV,userLoginTV;

    //-1 = matlab koi nahi hai selected
    // 1 = camera selected
    // 2 = user selected
    int selected;

    public static final String LOGIN_TYPE = "LOGIN_TYPE";
    public static final int DEVICE_LOGIN = 1;
    public static final int USER_LOGIN = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_of_login);
        init();
        ifDeviceLoginSelected();
        ifUserLoginSelected();
        getStartedButtonClick();
    }

    void init(){

        selected = -1;
        getStartedButton = findViewById(R.id.get_started);

        cameraLoginImg = findViewById(R.id.camera_login_icon);
        cameraLoginTV= findViewById(R.id.camera_text_tp);

        userLoginImg = findViewById(R.id.user_login_logo);
        userLoginTV= findViewById(R.id.user_text_tp);

        cameraCL = findViewById(R.id.device);
        userCL = findViewById(R.id.user);

    }

    void ifDeviceLoginSelected(){
        cameraCL.setOnClickListener(view -> {
            selected = 1;
            setSelected(cameraLoginTV,cameraLoginImg);
            setDeselected(userLoginTV,userLoginImg);
        });
    }

    void ifUserLoginSelected(){
        userCL.setOnClickListener(view -> {
            selected = 2;
            setSelected(userLoginTV,userLoginImg);
            setDeselected(cameraLoginTV,cameraLoginImg);
        });
    }


    void getStartedButtonClick(){
        getStartedButton.setOnClickListener(view -> {
            Intent intent = new Intent(this,LoginActivity.class);
            if(selected == -1){
                Toast.makeText(this, "No option Selected!", Toast.LENGTH_SHORT).show();
                return;
            }
            // if selected == 1 then open the device login page and pass string
            else if(selected == DEVICE_LOGIN){
                intent.putExtra(LOGIN_TYPE,DEVICE_LOGIN);
            }
            // else if selected == 2 then open the user login page and pass string
            else{
                intent.putExtra(LOGIN_TYPE,USER_LOGIN);
            }
            startActivity(intent);
        });
    }

    //set the text and image as highlighted
    void setSelected(TextView text,ImageView image){
        image.setColorFilter(ContextCompat.getColor(this, R.color.app_blue), android.graphics.PorterDuff.Mode.SRC_IN);
        text.setTextColor(ContextCompat.getColor(this, R.color.app_blue));
    }

    //set the text and image as grey or non highlighted
    void setDeselected(TextView text, ImageView image){
        image.setColorFilter(ContextCompat.getColor(this, R.color.light_grey), android.graphics.PorterDuff.Mode.SRC_IN);
        text.setTextColor(ContextCompat.getColor(this, R.color.light_grey));
    }
}