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
import java.util.ArrayList;
public class KlassementUitlezen extends AsyncTask<Void,Void,Void> {
    String data = "";
    Integer _parcourID;
    ArrayList<Tijd> klassement = new ArrayList<>();
    public KlassementUitlezen(Integer parcourID){
     _parcourID = parcourID;
    }
    protected Void doInBackground(Void... voids) {
        String URl = "http://ineke.broeders.be/themazerunner/Get.aspx?do=getranking&parcourid=" + _parcourID ;
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
                Tijd tijd = new Tijd();
                tijd.stand = (Integer) JO.get("Stand");
                tijd.naam = JO.get("Naam").toString();
                tijd.tijd = JO.get("Totaaltijd").toString();
                klassement.add(tijd);
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
        Integer einde = HTML.indexOf("$") -1;
        String JSON = HTML.substring(begin, einde).trim();
        return JSON;
    }
}
