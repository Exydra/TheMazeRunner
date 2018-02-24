package com.example.jan_c.themazerunner;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity{
    private Button inlogButton;
    private AutoCompleteTextView emailEditText;
    private EditText passwoordEditText;
    String leeg = "";
    String Email;
    String Paswoord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
         inlogButton = (Button) findViewById(R.id.email_sign_in_button);
        inlogButton.setOnClickListener(new inlogButtonClick());
        emailEditText = (AutoCompleteTextView) findViewById(R.id.email);
        passwoordEditText = (EditText) findViewById(R.id.password);

    }



    class inlogButtonClick implements View.OnClickListener {
        public void onClick(View view) {
if(Objects.equals(leeg ,emailEditText.getText().toString()) | Objects.equals(leeg,passwoordEditText.getText().toString())){
    AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(LoginActivity.this);
    dlgAlert.setMessage("Gelieve een e-mail adres en wachtwoord in te voeren");
    dlgAlert.setTitle("Email-wachtwoord");
    dlgAlert.setPositiveButton("OK", null);
    dlgAlert.setCancelable(true);
    dlgAlert.create().show();
} else {
    Email = emailEditText.getText().toString();
    Paswoord = passwoordEditText.getText().toString();
    Intent Kaart = new Intent(getApplicationContext(),Kaart.class);
    startActivity(Kaart);
}
        }
    }




}


