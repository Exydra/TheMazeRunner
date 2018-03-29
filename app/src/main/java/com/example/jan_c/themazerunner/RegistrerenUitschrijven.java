package com.example.jan_c.themazerunner;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by jan_c on 25/03/2018.
 */

public class RegistrerenUitschrijven extends AsyncTask<Void,Void,Void> {
    Loper loper;
    public  RegistrerenUitschrijven(String naam, String wachtwoord, String email){
        loper = new Loper();
      loper.naam = naam;
      loper.wachtwoord = wachtwoord;
      loper.email = email;
    }
    @Override
    protected Void doInBackground(Void... voids) {
        String URl = "http://ineke.broeders.be/themazerunner/Get.aspx?do=registreren&wachtwoord="+ loper.wachtwoord + "&email=" + loper.email + "&naam=" + loper.naam;
        HttpURLConnection httpURLConnection;
        try {
            URL url = new URL(URl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}