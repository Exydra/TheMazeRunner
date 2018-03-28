package com.example.jan_c.themazerunner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.concurrent.ExecutionException;

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
            try {
                RegistrerenUitschrijven registrerenUitschrijven = new RegistrerenUitschrijven(naamEditText.getText().toString(), wachtWoordEditText.getText().toString(), emailEditText.getText().toString());
                try {
                    registrerenUitschrijven.execute().get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                Intent loginActivity = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(loginActivity);

            }catch (Exception exeption){
                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(RegistrerenActivity.this);
                dlgAlert.setMessage("Het registreren is mislukt");
                dlgAlert.setTitle("Eror");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(false);
                dlgAlert.create().show();
            }
        }
    }
}

