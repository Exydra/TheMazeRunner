package com.example.jan_c.themazerunner;
import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.EmptyStackException;
import java.util.concurrent.ExecutionException;

public class RegistrerenUitschrijven extends AsyncTask<Void,Void,Void>{
    Loper loper;
    String data = "";
    String error = "";
    String error2 = "";
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
            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                data = data + line;
            }
            if (!HTMLtoJSON(data).equals("")){
                error = HTMLtoJSON(data);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            error2 = "error";
                e.printStackTrace();
        }
        return null;
    }
    public String HTMLtoJSON(String HTML){
        Integer begin = HTML.indexOf("%") + 2;
        Integer einde = HTML.indexOf("$") - 1;
        String JSON = HTML.substring(begin, einde).trim();
        return JSON;
    }
}
