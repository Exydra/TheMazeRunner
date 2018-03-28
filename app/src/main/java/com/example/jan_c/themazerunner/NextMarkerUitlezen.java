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

public class NextMarkerUitlezen extends AsyncTask<Void,Void,Void> {
    String data = "";
    Marker marker;
    Integer _parcourID;
    Integer _loperID;
    NextMarkerUitlezen nextMarkerUitlezen;
    public NextMarkerUitlezen (Integer parcourID, Integer loperID){
        _parcourID = parcourID;
       _loperID = loperID;
        marker = new Marker();
    }
    public Marker getNextMarker(){
        return nextMarkerUitlezen.marker;
    }
    @Override
    protected Void doInBackground(Void... voids) {
        String URl = "http://ineke.broeders.be/themazerunner/Get.aspx?do=nextmarker&parcourID=" + nextMarkerUitlezen._parcourID + "loperID=" + nextMarkerUitlezen._loperID;
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
                marker.markerID = (Integer) JO.get("MarkerID");
                marker.volgorde = (Integer) JO.get("Volgorde");
                marker.locatie = (LatLng) JO.get("Locatie");
                marker.parcourID = (Integer) JO.get("ParcourID");
                nextMarkerUitlezen.marker = marker;
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
