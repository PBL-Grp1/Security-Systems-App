package com.best_company.securitysystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ContactUsActivity extends AppCompatActivity {

    ImageView anandLI,anandIN,abhiLI,abhiIN,archishaLI,archishaIN,amritIN,amritLI,back;
    ImageView anandGIT,archishaGIT,abhiGIT,amritGIT;
    //email
    TextView anandEMAIL,archishaEMAIL,amritEMAIL,abhiEMAIL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        init();
        links();
        backPressed();
        copyToClipboardListeners();
    }



    public void init(){

        anandLI = findViewById(R.id.linkedANAND);
        anandIN = findViewById(R.id.instaANAND);
        anandGIT = findViewById(R.id.gitANAND);
        anandEMAIL = findViewById(R.id.anandEMAIL);


        archishaLI = findViewById(R.id.linkedarchisha);
        archishaIN = findViewById(R.id.instaarchisha);
        archishaGIT = findViewById(R.id.gitarchisha);
        archishaEMAIL = findViewById(R.id.archishaEMAIL);

        abhiIN = findViewById(R.id.instaABHI);
        abhiLI = findViewById(R.id.linkedABHI);
        abhiGIT = findViewById(R.id.gitABHI);
        abhiEMAIL = findViewById(R.id.abhiEMAIL);

        amritIN = findViewById(R.id.instaAMRIT);
        amritLI = findViewById(R.id.linkedAMRIT);
        amritGIT = findViewById(R.id.gitAMRIT);
        amritEMAIL = findViewById(R.id.amritEMAIL);

        back = findViewById(R.id.back);
    }

    private void links(){

        anandIN.setOnClickListener(view -> {
            Uri uri = Uri.parse("https://www.instagram.com/anand_bhalerao.a3b/?hl=en");
            startActivity(new Intent(Intent.ACTION_VIEW,uri));
        });

        anandLI.setOnClickListener(view -> {
            Uri uri = Uri.parse("https://www.linkedin.com/in/anand-bhalerao-0b69451bb/");
            startActivity(new Intent(Intent.ACTION_VIEW,uri));
        });

        anandGIT.setOnClickListener(view -> {
            Uri uri = Uri.parse("https://github.com/Anand-Avinash-Bhalerao");
            startActivity(new Intent(Intent.ACTION_VIEW,uri));
        });

        archishaIN.setOnClickListener(view -> {
            Uri uri = Uri.parse("https://instagram.com/archisha_02");
            startActivity(new Intent(Intent.ACTION_VIEW,uri));
        });

        archishaLI.setOnClickListener(view -> {
            Uri uri = Uri.parse("https://www.linkedin.com/in/archisha-mulmulay-a167b6218");
            startActivity(new Intent(Intent.ACTION_VIEW,uri));
        });

        archishaGIT.setOnClickListener(view -> {
            Uri uri = Uri.parse("https://github.com/archisha-02");
            startActivity(new Intent(Intent.ACTION_VIEW,uri));
        });

        abhiIN.setOnClickListener(view -> {
            Uri uri = Uri.parse("https://Instagram.com/abhishek.m_s");
            startActivity(new Intent(Intent.ACTION_VIEW,uri));
        });
        abhiLI.setOnClickListener(view -> {

        });
        abhiGIT.setOnClickListener(view -> {
            Uri uri = Uri.parse("https://github.com/ABHISHEKSOUNDALGEKAR");
            startActivity(new Intent(Intent.ACTION_VIEW,uri));
        });

        amritLI.setOnClickListener(view -> {
            Uri uri = Uri.parse("https://www.linkedin.com/in/amrit-singh-365310204");
            startActivity(new Intent(Intent.ACTION_VIEW,uri));
        });

        amritGIT.setOnClickListener(view -> {
            Uri uri = Uri.parse("https://github.com/iAmritSingh");
            startActivity(new Intent(Intent.ACTION_VIEW,uri));
        });
    }

    private void backPressed(){
        back.setOnClickListener(view -> {
            onBackPressed();
        });
    }
    private void copyToClipboardListeners() {
        anandEMAIL.setOnClickListener(view -> {
            String email = anandEMAIL.getText().toString();
            copyToClipBoard("Anand",email);
        });
        archishaEMAIL.setOnClickListener(view -> {
            String email = archishaEMAIL.getText().toString();
            copyToClipBoard("Archisha",email);
        });
        abhiEMAIL.setOnClickListener(view -> {
            String email = abhiEMAIL.getText().toString();
            copyToClipBoard("Abhishek",email);
        });
        amritEMAIL.setOnClickListener(view -> {
            String email = amritEMAIL.getText().toString();
            copyToClipBoard("Amrit",email);
        });
    }

    private void copyToClipBoard(String name,String email){
        Context context = getApplicationContext();
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Copied Text", email);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this, name+"'s email is copied!", Toast.LENGTH_SHORT).show();
    }

}
