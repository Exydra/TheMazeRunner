package com.example.jan_c.themazerunner;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by jan_c on 24/03/2018.
 */

public class AanmeldenUitlezen  extends AsyncTask<Void,Void,Void> {
    String data = "";
    String dataParsed = "";
    String singleParsed = "";
    Aanmelden aanmelden;
    public AanmeldenUitlezen (){
       aanmelden  = Aanmelden.getInstance();
    }
    @Override
    protected Void doInBackground(Void... voids) {
        String URl = "http://ineke.broeders.be/themazerunner/Get.aspx?do=aanmelden&wachtwoord=" + aanmelden._password  + "&email=" + aanmelden._email;
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
            data = HTMLtoJSON(data);
            JSONArray JA = new JSONArray(data);
            for (int i = 0; i < JA.length(); i++) {

                JSONObject JO = (JSONObject) JA.get(i);
                singleParsed = "LoperID:" + JO.get("LoperID") + "\n" +
                        "Naam:" + JO.get("Naam") + "\n" +
                        "Wachtwoord:" + JO.get("Wachtwoord") + "\n" +
                        "Email:" + JO.get("Email") + "\n";
                Loper loper = new Loper();
                loper.loperID = (Integer) JO.get("LoperID");
                loper.naam = JO.get("Naam").toString();
                loper.wachtwoord = JO.get("Wachtwoord").toString();
                loper.email = JO.get("Email").toString();
                aanmelden.loper.loperID = loper.loperID;
                aanmelden.loper.email = loper.email;
                aanmelden.loper.wachtwoord = loper.wachtwoord;
                aanmelden.loper.naam = loper.naam;
                dataParsed = dataParsed + singleParsed;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String HTMLtoJSON(String HTML){
        Integer begin = HTML.indexOf("%") + 1;
        Integer einde = HTML.indexOf("$");
        String JSON = HTML.substring(begin, einde).trim();
        return JSON;
    }
}
