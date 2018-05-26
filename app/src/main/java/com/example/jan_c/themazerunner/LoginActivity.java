package com.example.jan_c.themazerunner;
import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import java.util.Objects;
public class LoginActivity extends AppCompatActivity {
    private Button inlogButton;
    private Button registrereButton;
    private ProgressBar inloggenProgressBar;
    public AutoCompleteTextView emailEditText;
    public EditText passwoordEditText;
    String leeg = "";
    Aanmelden aanmelden;
    private static final Integer MY_PERMISSIONS_REQUEST_LOCATION = 0;
    private View mLayout;
    Intent kaartIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SharedPreferences sp1=this.getSharedPreferences("Login", MODE_PRIVATE);
        if(sp1.getString("email", null) == null | sp1.getString("wachtwoord", null) == null) {
            inlogButton = (Button) findViewById(R.id.email_sign_in_button);
            inlogButton.setOnClickListener(new inlogButtonClick());
            emailEditText = (AutoCompleteTextView) findViewById(R.id.email);
            passwoordEditText = (EditText) findViewById(R.id.password);
            registrereButton = (Button) findViewById(R.id.RegistrerenButton);
            registrereButton.setOnClickListener(new registrerenButtonClick());
            mLayout = findViewById(R.id.linearLayout);
            inloggenProgressBar = (ProgressBar) findViewById(R.id.inloggenProgressbar);
            inloggenProgressBar.setVisibility(View.INVISIBLE);
        } else {
            aanmelden = Aanmelden.getInstance();
            aanmelden._email = sp1.getString("email", null);
            aanmelden._password = sp1.getString("wachtwoord", null);
            aanmelden.BooleanToast=false;
            aanmelden.doAanmeldenUitlezen();
            kaartIntent = new Intent(getApplicationContext(), Kaart.class);
            startActivity(kaartIntent);
        }
    }
    class inlogButtonClick implements View.OnClickListener {
        public void onClick(View view) {
            //controleert of er locatie promision is
            if (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                requestCameraPermission();
            } else {
                inloggenProgressBar.setVisibility(View.VISIBLE);
                Handler handler2 = new Handler();
                handler2.postDelayed(new Runnable() {
                    public void run() {
                //logt de loper in
                try {
                    if (Objects.equals(leeg, emailEditText.getText().toString()) | Objects.equals(leeg, passwoordEditText.getText().toString())) {
                        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(LoginActivity.this);
                        dlgAlert.setMessage("Niet alles is ingevuld.");
                        dlgAlert.setTitle("Error");
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
                            kaartIntent = new Intent(getApplicationContext(), Kaart.class);
                            Aanmelden.getInstance().BooleanToast=false;
                            kaartIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            SharedPreferences sp=getSharedPreferences("Login", MODE_PRIVATE);
                            SharedPreferences.Editor Ed=sp.edit();
                            Ed.putString("email",loper.email);
                            Ed.putString("wachtwoord",loper.wachtwoord);
                            Ed.apply();
                            startActivity(kaartIntent);
                        } else {
                            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(LoginActivity.this);
                            dlgAlert.setMessage("U hebt het foute emailadres of wachtwoord ingevoerd.");
                            dlgAlert.setTitle("Error");
                            dlgAlert.setPositiveButton("OK", null);
                            dlgAlert.setCancelable(false);
                            dlgAlert.create().show();
                        }
                        inloggenProgressBar.setVisibility(View.INVISIBLE);
                    }
                } catch (Exception exeption) {
                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(LoginActivity.this);
                    dlgAlert.setMessage("Het inloggen is mislukt.");
                    dlgAlert.setTitle("Error");
                    inloggenProgressBar.setVisibility(View.INVISIBLE);
                    dlgAlert.setPositiveButton("OK", null);
                    dlgAlert.setCancelable(false);
                    dlgAlert.create().show();
                }
            }
        }, 500);
            }
        }
    }
    //opent regestreer pagina
    class registrerenButtonClick implements View.OnClickListener {
        public void onClick(View view) {
                Intent RegistrerenActivity = new Intent(getApplicationContext(), RegistrerenActivity.class);
                startActivity(RegistrerenActivity);
        }
    }
    //vraagt om permission en stelt deze in
    private void requestCameraPermission() {
        Snackbar.make(mLayout, "Deze app heeft heeft locatie nodig",
                Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Request the permission
                ActivityCompat.requestPermissions(LoginActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }).show();
    }
}


