package com.best_company.securitysystem.Activities.Login;

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

import com.best_company.securitysystem.R;

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
        copyEmailToClipboard();
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
        anandLinks();
        archishaLinks();
        abhishekLinks();
        amritLinks();

        openWebsite();
    }

    public void openWebsite(){
        TextView website = findViewById(R.id.websiteLink);
        website.setOnClickListener(view -> {
//            String url = "securitysystems.ezyro.com/security_system.html";
            String url = "www.google.com";
            Uri uriWeb = Uri.parse(url);
            startActivity(new Intent(Intent.ACTION_VIEW,uriWeb));
        });


    }
    void openTheLink(ImageView imageWithLogo,String link){
        imageWithLogo.setOnClickListener(view -> {
            Uri uri = Uri.parse(link);
            startActivity(new Intent(Intent.ACTION_VIEW,uri));
        });
    }

    void anandLinks(){

        openTheLink(anandIN,"https://www.instagram.com/anand_bhalerao.a3b/?hl=en");
        openTheLink(anandLI,"https://www.linkedin.com/in/anand-bhalerao-0b69451bb/");
        openTheLink(anandGIT,"https://github.com/Anand-Avinash-Bhalerao");
    }

    void archishaLinks(){

        openTheLink(archishaIN,"https://instagram.com/archisha_02");
        openTheLink(archishaLI,"https://www.linkedin.com/in/archisha-mulmulay-a167b6218");
        openTheLink(archishaGIT,"https://github.com/archisha-02");
    }

    void abhishekLinks(){
        openTheLink(abhiIN,"https://Instagram.com/abhishek.m_s");
        openTheLink(abhiLI,"");
        openTheLink(abhiGIT,"https://github.com/ABHISHEKSOUNDALGEKAR");
    }

    void amritLinks(){
        openTheLink(amritLI,"https://www.linkedin.com/in/amrit-singh-365310204");
        openTheLink(amritGIT,"https://github.com/iAmritSingh");
    }

    private void backPressed(){
        back.setOnClickListener(view -> {
            onBackPressed();
            finish();
        });
    }

    private void copyEmailToClipboard() {
        copySetOnClickListeners(anandEMAIL,"Anand");
        copySetOnClickListeners(archishaEMAIL,"Archisha");
        copySetOnClickListeners(abhiEMAIL,"Abhishek");
        copySetOnClickListeners(amritEMAIL,"Amrit");
    }

    // copies the text from text view and sends the email and name to the clipboard copy function
    void copySetOnClickListeners(TextView emailTextView, String name){
        emailTextView.setOnClickListener(view -> {
            String email = emailTextView.getText().toString();
            copyToClipBoard(name,email);
        });
    }

    //function to copy the email to clipboard
    private void copyToClipBoard(String name,String email){
        Context context = getApplicationContext();
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Copied Text", email);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this, name+"'s email is copied!", Toast.LENGTH_SHORT).show();
    }
}
