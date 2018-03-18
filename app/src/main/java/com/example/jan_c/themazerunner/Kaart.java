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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.support.v7.app.AppCompatActivity;

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
import java.util.List;



import static android.graphics.Color.rgb;
import static java.lang.StrictMath.toRadians;

public class Kaart extends FragmentActivity implements OnMapReadyCallback {
    public double counter = 0;
    private GoogleMap mMap;
    private LatLng huidigeLocatie;
    private Polyline polyline1a;
    private Polyline polyline1b;
    private Polyline polilyne1c;
    private Polyline polilyne1d;
    private Marker MarkerStart;
    private Marker MarkerStation;
    private Marker MarkerSTEM;
    private Marker MarkerMarkt;
    private Marker MarkerFinish;
    private Button RoutesButton;
    private TextView timerTextvieuw;
    private Button PauzeButton;
    private CountDownTimer timer;
    private Boolean gepauzeerd = false;
    private TextView afstandTextview;
    private ArrayList<LatLng> GepaseerdePunten;
    private Polyline Gepaseerd;
    private static final int PATTERN_GAP_LENGTH_PX = 20;
    private static final PatternItem GAP = new Gap(PATTERN_GAP_LENGTH_PX);
    private static final PatternItem DOT = new Dot();
    private static final List<PatternItem> DOTTED = Arrays.asList(DOT, GAP);
    public static TextView uitlezenText;


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


        uitlezenText = (TextView) findViewById(R.id.uitlezenText);
        fetchData process = new fetchData();
        process.execute();
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

        MarkerStart = mMap.addMarker(new MarkerOptions().position(new LatLng(51.161891212585495, 4.136264966509771))
                .title("Start")
        );
        MarkerStation = mMap.addMarker(new MarkerOptions().position(new LatLng(51.17039054011921, 4.142726591135215))
                .title("Station")
        );
        MarkerSTEM = mMap.addMarker(new MarkerOptions().position(new LatLng(51.16623048764471, 4.144357827015028))
                .title("STEM")
                .visible(false)
        );
        MarkerMarkt = mMap.addMarker(new MarkerOptions().position(new LatLng(51.16476175829673, 4.14110284877097))
                .title("Markt")
                .visible(false)
        );
        MarkerFinish = mMap.addMarker(new MarkerOptions().position(new LatLng(51.161891212585495, 4.136264966509771))
                .title("Finish")
                .visible(false)
        );
        // Add a marker in Sydney and move the camera
        polyline1a = mMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(new LatLng(51.161891212585495, 4.136264966509771),
                        new LatLng(51.16215866116135, 4.136771904013585),
                        new LatLng(51.16257917474246, 4.137313710562012),
                        new LatLng(51.16309313972857, 4.138180934842239),
                        new LatLng(51.163863502158144, 4.139504604950162),
                        new LatLng(51.16487679373538, 4.14096956144931),
                        new LatLng(51.16491716166276, 4.141339705965947),
                        new LatLng(51.16775212389541, 4.142041974555468),
                        new LatLng(51.17039054011921, 4.14272659135215))
                .visible(false)
        );
        polyline1b = mMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(new LatLng(51.17039054011921, 4.142726591135215),
                        new LatLng(51.17047039826386, 4.143024769073236),
                        new LatLng(51.17026648447245, 4.14324001651039),
                        new LatLng(51.16853341661673, 4.144846877443342),
                        new LatLng(51.16846690763632, 4.144748323574277),
                        new LatLng(51.168352542886, 4.144693338289471),
                        new LatLng(51.16823313230337, 4.144772463414483),
                        new LatLng(51.16816249493602, 4.144973629131528),
                        new LatLng(51.16623048764471, 4.144357827015028))
                .visible(false)
        );
        polilyne1c = mMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(new LatLng(51.16623048764471, 4.144357827015028),
                        new LatLng(51.165503423384166, 4.1440817945931485),
                        new LatLng(51.16525113164939, 4.143206053431641),
                        new LatLng(51.16502817056048, 4.142514513841888),
                        new LatLng(51.16492154495015, 4.141339218440407),
                        new LatLng(51.16476175829673, 4.14110284877097))
                .visible(false)
        );
        polilyne1d = mMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(new LatLng(51.16476175829673, 4.14110284877097),
                        new LatLng(51.163863502158144, 4.139504604950162),
                        new LatLng(51.16309313972857, 4.138180934842239),
                        new LatLng(51.16257917474246, 4.137313710562012),
                        new LatLng(51.16215866116135, 4.136771904013585),
                        new LatLng(51.161891212585495, 4.136264966509771))
                .visible(false)
        );
        stylePolyline(polyline1a);
        stylePolyline(polyline1b);
        stylePolyline(polilyne1c);
        stylePolyline(polilyne1d);

        final TextView timerText = findViewById(R.id.timerTekst);
        timer = new CountDownTimer(1000000000, 1000) {
            public void onTick(long millisUntilFinished) {
                timerText.setText(String.valueOf(counter));
                counter += 1;
                try {
                    if ((ActivityCompat.checkSelfPermission(Kaart.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(Kaart.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
                        mMap.setMyLocationEnabled(true);
                    }

                    // camera positie aanpassen
                    LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    Criteria criteria = new Criteria();

                    Location myLocation = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
                    huidigeLocatie = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
                    if (!gepauzeerd) {
                        CameraPosition cameraPosition = new CameraPosition.Builder()
                                .target(huidigeLocatie)
                                .zoom(18)
                                .bearing(myLocation.getBearing())
                                .build();
                        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    }
                    if (huidigeLocatie != null) {
                        GepaseerdePunten.add(huidigeLocatie);
                    }
                    if (GepaseerdePunten.size() != 0) {
                        Gepaseerd = mMap.addPolyline(new PolylineOptions()
                                .addAll(GepaseerdePunten)
                        );
                    }
                    stylePolyline(Gepaseerd);
                    if (afstand(huidigeLocatie, MarkerStation.getPosition()) < 0.005) {
                        MarkerStart.setVisible(false);
                        MarkerStation.setVisible(false);
                        MarkerSTEM.setVisible(true);
                    }
                    if (afstand(huidigeLocatie, MarkerSTEM.getPosition()) < 0.005) {
                        MarkerSTEM.setVisible(false);
                        MarkerMarkt.setVisible(true);
                    }
                    if (afstand(huidigeLocatie, MarkerMarkt.getPosition()) < 0.005) {
                        MarkerMarkt.setVisible(false);
                        MarkerFinish.setVisible(true);
                    }

                } catch (Exception Locatie) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(Kaart.this);
                    builder1.setMessage(Locatie.getMessage());
                    builder1.setCancelable(true);
                    builder1.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
            }

            public void onFinish() {
            }
        }.start();
    }

    private void stylePolyline(Polyline polyline) {
        polyline.setWidth(20);
        polyline.setColor(rgb(146, 46, 76));
        polyline.setJointType(JointType.ROUND);
        polyline.setPattern(DOTTED);
    }


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

    public static double haversin(double val) {
        return Math.pow(Math.sin(val / 2), 2);
    }

    class routesButtonClick implements View.OnClickListener {
        public void onClick(View view) {
            Intent Routes = new Intent(getApplicationContext(), RoutesKiezen.class);
            startActivity(Routes);
        }
    }

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


