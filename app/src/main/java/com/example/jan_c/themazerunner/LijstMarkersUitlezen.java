package com.example.jan_c.themazerunner;

import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;

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
 * Created by jan_c on 27/03/2018.
 */

public class LijstMarkersUitlezen extends AsyncTask<Void,Void,Void> {
    String data = "";
    LijstMarkers lijstMarkers;
    public LijstMarkersUitlezen(){
        lijstMarkers  = LijstMarkers.getInstance();
    }
    @Override
    protected Void doInBackground(Void... voids) {
        String URl = "http://ineke.broeders.be/themazerunner/Get.aspx?Do=getmarkerlijst&parcourid="+lijstMarkers.parcourID;
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
                Marker marker = new Marker();
                marker.parcourID = (Integer) JO.get("ParcourID");
                marker.locatie = (LatLng) JO.get("Locatie");
                marker.markerID = (Integer) JO.get("MarkerID");
                marker.volgorde = (Integer) JO.get("Volgorde");
                lijstMarkers.lijstmarkers.add(marker);
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

