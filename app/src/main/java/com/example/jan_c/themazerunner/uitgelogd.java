package com.example.jan_c.themazerunner;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class uitgelogd extends AppCompatActivity {
    private TextView uitloggen;
    private ProgressBar loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uitgelogd);
        uitloggen = (TextView) findViewById(R.id.uitloggentextView);
        loader = (ProgressBar) findViewById(R.id.uitloggenprogressBar);


        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            public void run() {
                uitloggen.setText("U bent uitgelogd!");
               loader.setVisibility(View.INVISIBLE);

                Handler handler2 = new Handler();
                handler2.postDelayed(new Runnable() {
                    public void run() {

                        Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                        login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(login);
                    }
                }, 1000);

            }
        }, 1500);

    }
    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }


}
