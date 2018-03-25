package com.example.jan_c.themazerunner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * A login screen that offers login via email/password.
 */
public class RegistrerenActivity extends AppCompatActivity {
    Button registrerenButton;
    EditText emailEditText;
    EditText wachtWoordEditText;
    EditText naamEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registreren);
        registrerenButton = (Button) findViewById(R.id.RegistrerenButton);
        registrerenButton.setOnClickListener(new registrerenButtonClick());
        emailEditText = (EditText) findViewById(R.id.EmailEditText);
        wachtWoordEditText = (EditText) findViewById(R.id.WachtwoordEditText);
        naamEditText = (EditText) findViewById(R.id.NaamEditText);
    }
    class registrerenButtonClick implements View.OnClickListener {
        public void onClick(View view) {
            Registreren registreren = Registreren.getInstance();
            registreren.loper.naam = naamEditText.getText().toString();
            registreren.loper.email = emailEditText.getText().toString();
            registreren.loper.wachtwoord = wachtWoordEditText.getText().toString();
            registreren.doRegistrerenUitschrijven();
            Intent loginActivity = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(loginActivity);
        }}
}

