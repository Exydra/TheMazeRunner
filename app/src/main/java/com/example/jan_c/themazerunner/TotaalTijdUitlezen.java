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
    String totaaltijd;
    String error = "";
    public TotaalTijdUitlezen(Integer parcourID, Integer loperID){
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
            if (!HTMLtoJSON(data).equals("")){
                error = HTMLtoJSON(data);
            }
            data = HTMLtoJSON(data);
            JSONArray JA = new JSONArray(data);
            totaaltijd = data;
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
        Integer begin = HTML.indexOf("%") + 2;
        Integer einde = HTML.indexOf("$")-1;
        String JSON = HTML.substring(begin, einde).trim();
        return JSON;
    }
}
