package com.example.jan_c.themazerunner;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by jan_c on 27/03/2018.
 */
public class LijstMarkers {
    List<Marker> lijstmarkers;
    LijstMarkersUitlezen lijstMarkersUitlezen;
    Integer parcourID;

    private LijstMarkers(){}

    private static LijstMarkers instance = null;
    public static LijstMarkers getInstance()  {
        if(instance == null) {
            instance = new LijstMarkers();
        }
        return instance;
    }

    public void doLijstMarkersUitezen(){
        instance.lijstMarkersUitlezen = new LijstMarkersUitlezen();
        try {
            instance.lijstMarkersUitlezen.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
