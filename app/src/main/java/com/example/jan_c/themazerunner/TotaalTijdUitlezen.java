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

public class TotaalTijdUitlezen extends AsyncTask<Void,Void,Void> {
    String data = "";
    Integer _parcourID;
    Integer _loperID;
    String error = "";
    Tijd tijd;
    Tijd eigenscore;
    public TotaalTijdUitlezen(Integer parcourID, Integer loperID){
        tijd = new Tijd();
        _parcourID = parcourID;
        _loperID = loperID;
    }
    protected Void doInBackground(Void... voids) {
        String URl = "http://ineke.broeders.be/themazerunner/Get.aspx?do=gettotaaltijd&Loperid=" + _loperID + "&Parcourid="  + _parcourID;
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
          //  totaaltijd = data;
            JSONArray JA = new JSONArray(data);
            for (int i = 0; i < JA.length(); i++) {

                JSONObject JO = (JSONObject) JA.get(i);
                eigenscore = new Tijd();
                eigenscore.stand = (Integer) JO.get("Stand");
                eigenscore.tijd = JO.get("Totaaltijd").toString();


            }
            if (eigenscore.tijd.equals("00:00:00")){
                error = eigenscore.tijd;
            } else {
                tijd.stand = eigenscore.stand;
                tijd.tijd = eigenscore.tijd;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (JSONException e) {
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
