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
import java.util.ArrayList;
import static java.lang.Double.parseDouble;
public class LijstMarkersUitlezen extends AsyncTask<Void,Void,Void> {
    String data = "";
    ArrayList<Marker> lijstmarkers;
    Integer _parcourID;
    public LijstMarkersUitlezen(Integer parcourID){
         _parcourID = parcourID;
        lijstmarkers = new ArrayList<>();
         }
    @Override
    protected Void doInBackground(Void... voids) {
        String URl = "http://ineke.broeders.be/themazerunner/Get.aspx?Do=getmarkerlijst&parcourid="+_parcourID;
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
                marker.locatie = new LatLng(parseDouble(Lat(JO.get("Locatie").toString())), parseDouble(Lng(JO.get("Locatie").toString()))) ;
                marker.markerID = (Integer) JO.get("MarkerID");
                marker.volgorde = (Integer) JO.get("Volgorde");
                lijstmarkers.add(marker);
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
        Integer begin = HTML.indexOf("%") + 2;
        Integer einde = HTML.indexOf("$")-1;
        String JSON = HTML.substring(begin, einde).trim();
        return JSON;
    }
    public String Lat(String latLng){
        Integer komma = latLng.indexOf(",");
        return latLng.substring(0,komma-1);
    }
    public String Lng(String latLng){
        Integer komma = latLng.indexOf(",");
        return latLng.substring(komma+1);
    }
}

