package com.example.jan_c.themazerunner;
import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
public class UpdateTimeUitshrijven  extends AsyncTask<Void,Void,Void> {
    String data = "";
    Integer loperID;
    Integer _markerID;
    public UpdateTimeUitshrijven(Integer markerID){
       loperID = Aanmelden.getInstance().loper.loperID;
       _markerID = markerID;
    }
    protected Void doInBackground(Void... voids) {
        String URl = "http://ineke.broeders.be/themazerunner/Get.aspx?do=updatetijdstipaankomst&loperid="+ loperID + "&markerid=" + _markerID;
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
