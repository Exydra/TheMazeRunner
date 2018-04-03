package com.example.jan_c.themazerunner;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static android.graphics.Color.rgb;
import static java.lang.StrictMath.toRadians;

public class Kaart extends FragmentActivity implements OnMapReadyCallback{
    public double counter = 0;
    private GoogleMap mMap;
    private LatLng huidigeLocatie;
    private Button RoutesButton;
    private Button PauzeButton;
    private CountDownTimer timer;
    private Boolean gepauzeerd = false;
    private ArrayList<LatLng> GepaseerdePunten;
    private Polyline Gepaseerd;
    private static final int PATTERN_GAP_LENGTH_PX = 20;
    private static final PatternItem GAP = new Gap(PATTERN_GAP_LENGTH_PX);
    private static final PatternItem DOT = new Dot();
    private static final List<PatternItem> DOTTED = Arrays.asList(DOT, GAP);
    public static TextView uitlezenText;
    private Boolean geenLocatieError = false;
    private Integer routeID = 0;
    private  ArrayList<com.example.jan_c.themazerunner.Marker> lijstmarkers;
    private LijstMarkersUitlezen lijstMarkersUitlezen;
    private Integer aantalMarkers;
    private Marker marker;
    private Integer couterMarkers = 0;
    private TextView toastTekst;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kaart);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        RoutesButton = findViewById(R.id.RoutesButton);
        RoutesButton.setOnClickListener(new routesButtonClick());
        PauzeButton = findViewById(R.id.Pauzebutton);
        PauzeButton.setOnClickListener(new PauzeButtonClick());
        GepaseerdePunten = new ArrayList<>();

        // geeft de gebruikersnaam weer
        uitlezenText = (TextView) findViewById(R.id.uitlezenText);
        uitlezenText.setText(Aanmelden.getInstance().loper.naam);

        //zet de markers vann de gekozen route in een lijst
        routeID = getIntent().getIntExtra("parcourID", 0);
        if (routeID !=0){
            lijstMarkersUitlezen = new LijstMarkersUitlezen(routeID);
            try {
                lijstMarkersUitlezen.execute().get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            lijstmarkers = lijstMarkersUitlezen.lijstmarkers;
            aantalMarkers = lijstmarkers.size();
        }

        //BegroetingsToast
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_root));
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0,0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toastTekst = (TextView) toast.getView().findViewById(R.id.toast_text);
        if(timeOfDay >= 0 && timeOfDay < 12){
            toastTekst.setText("Goedemorgen " + Aanmelden.getInstance().loper.naam + "!");
        }else if(timeOfDay >= 12 && timeOfDay < 16){
            toastTekst.setText("Goedenmiddag " + Aanmelden.getInstance().loper.naam + "!");
        }else if(timeOfDay >= 16 && timeOfDay < 21){
            toastTekst.setText("Goedenavond " + Aanmelden.getInstance().loper.naam + "!");
        }else if(timeOfDay >= 21 && timeOfDay < 24){
            toastTekst.setText("Goede nacht " + Aanmelden.getInstance().loper.naam + "!");
        }
        toast.show();
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
    public void onMapReady(final GoogleMap googleMap) {

            mMap = googleMap;

            //timer
            final TextView timerText = findViewById(R.id.timerTekst);
            timer = new CountDownTimer(2000000000, 1000) {
                public void onTick(long millisUntilFinished) {

                    //controleeerd of de loper nog niet is gefinisht
                    if (couterMarkers != aantalMarkers) {

                        timerText.setText(String.valueOf(counter));
                        counter += 1;
                        try {

                            // zet locatie aan als er toesteming is
                            if ((ActivityCompat.checkSelfPermission(Kaart.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(Kaart.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
                                mMap.setMyLocationEnabled(true);
                            }

                            //huidige locatie
                            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                            Criteria criteria = new Criteria();
                            Location myLocation = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
                            huidigeLocatie = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());

                            //update camera postie als er niet is gepauzeerd
                            if (!gepauzeerd) {
                                CameraPosition cameraPosition = new CameraPosition.Builder()
                                        .target(huidigeLocatie)
                                        .zoom(18)
                                        .bearing(myLocation.getBearing())
                                        .build();
                                mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                            }

                            //plaatst de gepaseerde punten in een lijst
                            if (routeID != 0) {
                                if (huidigeLocatie != null) {
                                    GepaseerdePunten.add(huidigeLocatie);
                                }

                                //plaatst de polyline met de gepaseerde punten op de kaart
                                if (GepaseerdePunten.size() != 0) {
                                    Gepaseerd = mMap.addPolyline(new PolylineOptions()
                                            .addAll(GepaseerdePunten)
                                    );
                                }
                                stylePolyline(Gepaseerd);

                                //plaatst de marker op de kaart
                                marker = mMap.addMarker(new MarkerOptions().position(lijstmarkers.get(couterMarkers).locatie));

                                //kijkt of de loper aan de marker is
                                if (afstand(huidigeLocatie, marker.getPosition()) < 0.005) {
                                    couterMarkers += 1;
                                }
                            }

                            //handelt de exeption af
                        } catch (Exception Locatie) {
                            if (!geenLocatieError) {
                                geenLocatieError = true;
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(Kaart.this);
                                builder1.setMessage("Er is geen locatie beschikbaar");
                                builder1.setCancelable(false);
                                builder1.setPositiveButton(
                                        "OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                geenLocatieError = false;
                                                dialog.cancel();
                                            }
                                        });

                                AlertDialog alert11 = builder1.create();
                                alert11.show();
                            }
                        }
                    } else {
                        timer.cancel();
                    }

                }

                public void onFinish() {
                }

            }.start();
        }

   //stelt de stijl van de polyline in
    private void stylePolyline(Polyline polyline) {
        polyline.setWidth(20);
        polyline.setColor(rgb(146, 46, 76));
        polyline.setJointType(JointType.ROUND);
        polyline.setPattern(DOTTED);
    }

   //berekend de afstand tussen 2 punten
    public double afstand(LatLng p1, LatLng p2) {
        double R = 6371;
        double dLat = toRadians(p2.latitude - p1.latitude);
        double dLong = toRadians(p2.longitude - p1.longitude);
        double p1Lat = toRadians(p1.latitude);
        double p2Lat = toRadians(p2.latitude);
        double a = haversin(dLat) + Math.cos(p1Lat) * Math.cos(p2Lat) * haversin(dLong);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
    //past de formule van haversin toe
    public static double haversin(double val) {
        return Math.pow(Math.sin(val / 2), 2);
    }

    //cick event voor een route te seleceteren
    class routesButtonClick implements View.OnClickListener {
        public void onClick(View view) {
            Intent Routes = new Intent(getApplicationContext(), RoutesKiezen.class);
            startActivity(Routes);
        }
    }

    //click event zodat de camera uitzoemt en niet meer volgt
    class PauzeButtonClick implements View.OnClickListener {
        public void onClick(View view) {
            if (!gepauzeerd) {
                gepauzeerd = true;
                PauzeButton.setText("Hervatten");
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(huidigeLocatie)
                        .zoom(13)
                        .build();
                mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            } else {
                timer.start();
                gepauzeerd = false;
                PauzeButton.setText("Bekijk");
            }
        }
    }

}


