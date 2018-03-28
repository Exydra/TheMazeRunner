package com.example.jan_c.themazerunner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import java.util.Objects;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity{
    private Button inlogButton;
    private Button registrereButton;
    public AutoCompleteTextView emailEditText;
    public EditText passwoordEditText;
    String leeg = "";
    Aanmelden aanmelden;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
         inlogButton = (Button) findViewById(R.id.email_sign_in_button);
        inlogButton.setOnClickListener(new inlogButtonClick());
        emailEditText = (AutoCompleteTextView) findViewById(R.id.email);
        passwoordEditText = (EditText) findViewById(R.id.password);
        registrereButton = (Button) findViewById(R.id.RegistrerenButton);
        registrereButton.setOnClickListener(new registrerenButtonClick());
    }



    class inlogButtonClick implements View.OnClickListener {
        public void onClick(View view) {
            try {
                if (Objects.equals(leeg, emailEditText.getText().toString()) | Objects.equals(leeg, passwoordEditText.getText().toString())) {
                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(LoginActivity.this);
                    dlgAlert.setMessage("Gelieve een e-mail adres en wachtwoord in te voeren");
                    dlgAlert.setTitle("Email-wachtwoord");
                    dlgAlert.setPositiveButton("OK", null);
                    dlgAlert.setCancelable(false);
                    dlgAlert.create().show();
                } else {
                    aanmelden = Aanmelden.getInstance();
                    aanmelden._email = emailEditText.getText().toString();
                    aanmelden._password = passwoordEditText.getText().toString();
                    aanmelden.doAanmeldenUitlezen();
                    Loper loper = aanmelden.loper;
                    if (loper.email.equals(aanmelden._email) & loper.wachtwoord.equals(aanmelden._password)) {
                        Intent Kaart = new Intent(getApplicationContext(), Kaart.class);
                        startActivity(Kaart);
                    } else {
                        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(LoginActivity.this);
                        dlgAlert.setMessage("U hebt het foute emailadres of wachtwoord ingevoerd");
                        dlgAlert.setTitle("Email-wachtwoord");
                        dlgAlert.setPositiveButton("OK", null);
                        dlgAlert.setCancelable(false);
                        dlgAlert.create().show();
                    }
                }
            }catch (Exception exeption){
                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(LoginActivity.this);
                dlgAlert.setMessage("Het inloggen is mislukt");
                dlgAlert.setTitle("Error");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(false);
                dlgAlert.create().show();
            }
        }
    }

    class registrerenButtonClick implements View.OnClickListener {
        public void onClick(View view) {
            Intent RegistrerenActivity = new Intent(getApplicationContext(), RegistrerenActivity.class);
            startActivity(RegistrerenActivity);
        }}

}


