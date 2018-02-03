package com.example.jan_c.themazerunner;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.text.DecimalFormat;

public class Kaart extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kaart);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.addMarker(new MarkerOptions().position(new LatLng(51.161891212585495, 4.136264966509771))
                .title("Start")
        );
        mMap.addMarker(new MarkerOptions().position(new LatLng(51.17039054011921,4.142726591135215))
                .title("Station")
        );
        mMap.addMarker(new MarkerOptions().position(new LatLng(51.16623048764471,4.144357827015028))
                .title("STEM")
        );
        mMap.addMarker(new MarkerOptions().position(new LatLng(51.16476175829673,4.14110284877097))
                .title("Markt")
        );
        mMap.addMarker(new MarkerOptions().position(new LatLng(51.161891212585495, 4.136264966509771))
                .title("Finish")
        );
        // Add a marker in Sydney and move the camera
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.161891212585495,4.136264966509771 ), 16));
        Polyline polilyne1a = mMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add( new LatLng( 51.161891212585495,4.136264966509771 ),
                      new LatLng( 51.16215866116135,4.136771904013585 ),
                       new LatLng(51.16257917474246,4.137313710562012 ),
                       new LatLng(51.16309313972857,4.138180934842239),
                       new LatLng(51.163863502158144,4.139504604950162 ),
                       new LatLng(51.16487679373538,4.14096956144931) ,
                        new LatLng(51.16491716166276,4.141339705965947),
                       new LatLng(51.16775212389541,4.142041974555468) ,
                       new LatLng(51.17039054011921,4.14272659135215) )
        );
        Polyline polilyne1b = mMap.addPolyline(new PolylineOptions()
                .clickable(true)
        .add( new LatLng( 51.17039054011921,4.142726591135215 ),
                new LatLng( 51.17047039826386,4.143024769073236 ),
                new LatLng( 51.17026648447245,4.14324001651039),
                new LatLng(51.16853341661673,4.144846877443342),
                new LatLng( 51.16846690763632,4.144748323574277),
                new LatLng(51.168352542886,4.144693338289471) ,
                new LatLng(51.16823313230337,4.144772463414483),
                new LatLng(51.16816249493602,4.144973629131528) ,
                new LatLng(51.16623048764471,4.144357827015028) )
        );
        Polyline polilyne1c = mMap.addPolyline(new PolylineOptions()
                .clickable(true)
         .add( new LatLng(  51.16623048764471,4.144357827015028),
                new LatLng( 51.165503423384166,4.1440817945931485),
                new LatLng( 51.16525113164939,4.143206053431641),
                new LatLng(51.16502817056048,4.142514513841888),
                new LatLng(51.16492154495015,4.141339218440407 ),
                new LatLng(51.16476175829673,4.14110284877097))
        );
        Polyline polilyne1d = mMap.addPolyline(new PolylineOptions()
                .clickable(true)
        .add( new LatLng( 51.16476175829673,4.14110284877097 ),
                new LatLng(51.163863502158144,4.139504604950162 ),
                new LatLng(51.16309313972857,4.138180934842239 ),
                new LatLng(51.16257917474246,4.137313710562012),
                new LatLng(51.16215866116135,4.136771904013585  ),
                new LatLng(51.161891212585495,4.136264966509771 ))
        );
    }
}
