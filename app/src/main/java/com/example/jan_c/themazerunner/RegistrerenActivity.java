package com.example.jan_c.themazerunner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

/**
 * A login screen that offers login via email/password.
 */
public class RegistrerenActivity extends AppCompatActivity {
    Button registrerenButton;
    EditText emailEditText;
    EditText wachtWoordEditText;
    EditText naamEditText;
    String leeg = "";
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

                if (Objects.equals(leeg, emailEditText.getText().toString()) | Objects.equals(leeg, wachtWoordEditText.getText().toString()) | Objects.equals(leeg, naamEditText.getText().toString()))
                {
                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(RegistrerenActivity.this);
                    dlgAlert.setMessage("Niet alles is ingevuld.");
                    dlgAlert.setTitle("Error");
                    dlgAlert.setPositiveButton("OK", null);
                    dlgAlert.setCancelable(false);
                    dlgAlert.create().show();

                }
                else
                if (emailTest(emailEditText.getText().toString()) == 0)

                {
                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(RegistrerenActivity.this);
                    dlgAlert.setMessage("Gelieve een correct e-mailadres in te vullen.");
                    dlgAlert.setTitle("Error");
                    dlgAlert.setPositiveButton("OK", null);
                    dlgAlert.setCancelable(false);
                    dlgAlert.create().show();
                }



                else {
                    {
                        RegistrerenUitschrijven registrerenUitschrijven = new RegistrerenUitschrijven(naamEditText.getText().toString(), wachtWoordEditText.getText().toString(), emailEditText.getText().toString());
                        try {
                            registrerenUitschrijven.execute().get();

                            if (registrerenUitschrijven.error.equals("Dit e-mail adres word al reeds gebruikt , gelieven een ander optegeven of inteloggen met uw bestaande."))
                            {
                                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(RegistrerenActivity.this);
                                dlgAlert.setMessage("Dit e-mail adres bestaat al.");
                                dlgAlert.setTitle("Error");
                                dlgAlert.setPositiveButton("OK", null);
                                dlgAlert.setCancelable(false);
                                dlgAlert.create().show();
                            }
                            else
                            {
                                Intent loginActivity = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(loginActivity);
                            }

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }

                    }}

            }catch (Exception exeption){
                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(RegistrerenActivity.this);
                dlgAlert.setMessage("Het registreren is mislukt.");
                dlgAlert.setTitle("Error");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(false);
                dlgAlert.create().show();
            }
        }
    }

    //controle email
    public int emailTest(String email) {
        Integer apenstaartje = email.toString().indexOf("@");
        Integer punt = email.toString().indexOf(".");
        Integer lengteEmail = email.toString().length();


        Integer Resultaat = 0;
        if (apenstaartje == -1 | punt == -1)
        {
            Resultaat = 0;
        }
        else
        {
            Resultaat = 1;
        }
        // controle voor letters voor het apenstaartje
        if (apenstaartje == 0) {Resultaat = 0;}
        // controle voor letters tussen apenstaartje en punt
        if (punt - apenstaartje == 1) {Resultaat = 0;}
        // controle voor letters na het punt
        if (lengteEmail - punt == 1 | lengteEmail - punt == 2) {Resultaat = 0;}

        return Resultaat;
    }




}

