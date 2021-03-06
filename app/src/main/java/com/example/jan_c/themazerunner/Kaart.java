package com.example.jan_c.themazerunner;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;
import static android.graphics.Color.rgb;
import static java.lang.StrictMath.toRadians;
public class Kaart extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {
    private DrawerLayout mDrawerLayout;
    public double counter = 0;
    private GoogleMap mMap;
    private LatLng huidigeLocatie;
    private Button PauzeButton;
    private CountDownTimer timer;
    private Boolean gepauzeerd = false;
    private ArrayList<LatLng> GepaseerdePunten;
    private Polyline Gepaseerd;
    private Boolean geenLocatieError = false;
    private Integer routeID = 0;
    private  ArrayList<com.example.jan_c.themazerunner.Marker> lijstmarkers;
    private LijstMarkersUitlezen lijstMarkersUitlezen;
    private Integer aantalMarkers;
    private com.google.android.gms.maps.model.Marker marker;
    private Integer couterMarkers = 0;
    private TextView toastTekst;
    private TextView gebruikersNaamTextview;
    private TextView emailTextview;
    private TextView afstandTotVolgendePuntTextView;
    private TextView volgendePuntTextView;
    private   Location myLocation;
    private LatLngBounds bounds;
    private  CameraPosition cameraPosition;
    final static int GLOBE_WIDTH = 256; // a constant in Google's map projection
    final static int ZOOM_MAX = 21;
    private Fragment map;
    private ProgressBar loader;
    private RelativeLayout relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        loader = (ProgressBar) findViewById(R.id.klassementProgressbar);
        loader.setVisibility(View.INVISIBLE);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerLayout.addDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        // Respond when the drawer's position changes
                    }
                    @Override
                    public void onDrawerOpened(View drawerView) {
                        //Respond when the drawer's position open
                        gebruikersNaamTextview = findViewById(R.id.gebruikersnaamTextview);
                        gebruikersNaamTextview.setText(Aanmelden.getInstance().loper.naam);
                        emailTextview = findViewById(R.id.emailTextview);
                        emailTextview.setText(Aanmelden.getInstance().loper.email);
                    }
                    @Override
                    public void onDrawerClosed(View drawerView) {
                        // Respond when the drawer is closed
                    }
                    @Override
                    public void onDrawerStateChanged(int newState) {
                        // Respond when the drawer motion state changes
                    }
                }
        );
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        PauzeButton = findViewById(R.id.Pauzebutton);
        PauzeButton.setOnClickListener(new PauzeButtonClick());
        GepaseerdePunten = new ArrayList<>();
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
        afstandTotVolgendePuntTextView = findViewById(R.id.afstandTextview);
        volgendePuntTextView = findViewById(R.id.VolgendePuntTextview);
        volgendePuntTextView.setVisibility(View.INVISIBLE);
        map = getSupportFragmentManager().findFragmentById(R.id.map);
        relativeLayout = findViewById(R.id.relativeLayout3);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_routes) {
            Intent routesKiezenIntent = new Intent(getApplicationContext(), RoutesKiezen.class);
            startActivity(routesKiezenIntent);
        } else if (id == R.id.nav_logout) {
        // uitloggen & variabelen resettens
            Aanmelden.instance = null;
            Intent uitloggen = new Intent(getApplicationContext(), uitgelogd.class);
            uitloggen.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            Aanmelden.getInstance().BooleanToast = true;
            SharedPreferences preferences = getSharedPreferences("Login", MODE_PRIVATE);
            preferences.edit().remove("email").apply();
            preferences.edit().remove("wachtwoord").apply();
            timer.cancel();
            startActivity(uitloggen);
        } else if (id == R.id.nav_help) {
            Intent help = new Intent(getApplicationContext(), help.class);
            startActivity(help);
        }
        else if (id == R.id.nav_ranking){
            loader.setVisibility(View.VISIBLE);
            Handler handler2 = new Handler();
            handler2.postDelayed(new Runnable() {
                public void run() {
                    loader.setVisibility(View.INVISIBLE);
                    Intent ranking = new Intent(getApplicationContext(), ranking.class);
                    startActivity(ranking);

                }
            }, 1000);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        //timer
        final TextView timerText = findViewById(R.id.timerTekst);
        loader.setVisibility(View.VISIBLE);
        Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            public void run() {
                loader.setVisibility(View.INVISIBLE);
            }
        }, 1000);
        timer = new CountDownTimer(2000000000, 3000) {
            @SuppressLint("SetTextI18n")
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
                         myLocation = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
                        huidigeLocatie = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
                        //update camera postie als er niet is gepauzeerd
                        if (!gepauzeerd & routeID==0) {
                             CameraPosition cameraPosition = new CameraPosition.Builder()
                                    .target(huidigeLocatie)
                                    .zoom(18)
                                    .bearing(myLocation.getBearing())
                                    .build();
                            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                        }
                        //plaatst de gepaseerde punten in een lijst
                        if (routeID != 0) {
                            volgendePuntTextView.setVisibility(View.VISIBLE);
                            relativeLayout.setVisibility(View.VISIBLE);
                            PauzeButton.setVisibility(View.VISIBLE);
                            if (huidigeLocatie != null) {
                                if(GepaseerdePunten.size() == 0){
                                    GepaseerdePunten.add(huidigeLocatie);
                                } else {
                                    if (afstand(huidigeLocatie, GepaseerdePunten.get(GepaseerdePunten.size() - 1)) > 0.020) {
                                        GepaseerdePunten.add(huidigeLocatie);
                                    }
                                }
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
                            if(!gepauzeerd){
                                 bounds = LatLngBounds.builder ()
                                        .include(huidigeLocatie)
                                        .include(marker.getPosition())
                                        .build();
                                 cameraPosition = new CameraPosition.Builder()
                                        .bearing(myLocation.getBearing())
                                        .target(bounds.getCenter())
                                        .zoom(getBoundsZoomLevel(bounds.northeast, bounds.southwest,map.getView().getWidth()/4, map.getView().getHeight()/4))
                                        .build();
                                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                                    }
                            //berekend afstand tot volgende punt
                            Double afstand = afstand(huidigeLocatie,marker.getPosition());
                            DecimalFormat decimalFormat = new DecimalFormat("#0.00");
                            afstandTotVolgendePuntTextView.setText(decimalFormat.format(afstand)+" km");
                            //kijkt of de loper aan de marker is
                            if (afstand(huidigeLocatie, marker.getPosition()) < 0.020) {
                                //update time
                                UpdateTimeUitshrijven updateTimeUitshrijven = new UpdateTimeUitshrijven(lijstmarkers.get(couterMarkers).markerID);
                                try {
                                    updateTimeUitshrijven.execute().get();
                                }catch (InterruptedException e) {
                                    e.printStackTrace();
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                }
                                marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                                Toast.makeText(getBaseContext(),"U heeft de marker bereikt",Toast.LENGTH_LONG).show();
                                couterMarkers += 1;
                            }
                        }
                        if(!geenLocatieError & Aanmelden.getInstance().BooleanToast == false & loader.getVisibility() == View.INVISIBLE) {
                            //BegroetingsToast
                            Calendar c = Calendar.getInstance();
                            int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
                            LayoutInflater inflater = getLayoutInflater();
                            View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_root));
                            Toast toast = new Toast(getApplicationContext());
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.setDuration(Toast.LENGTH_LONG);
                            toast.setView(layout);
                            toastTekst = (TextView) toast.getView().findViewById(R.id.toast_text);
                            if (timeOfDay >= 0 && timeOfDay < 12) {
                                toastTekst.setText("Goedemorgen " + Aanmelden.getInstance().loper.naam + " !");
                            } else if (timeOfDay >= 12 && timeOfDay < 16) {
                                toastTekst.setText("Goedenmiddag " + Aanmelden.getInstance().loper.naam + " !");
                            } else if (timeOfDay >= 16 && timeOfDay < 21) {
                                toastTekst.setText("Goedenavond " + Aanmelden.getInstance().loper.naam + " !");
                            } else if (timeOfDay >= 21 && timeOfDay < 24) {
                                toastTekst.setText("Goede nacht " + Aanmelden.getInstance().loper.naam + " !");
                            }
                            Aanmelden.getInstance().BooleanToast = true;
                            toast.show();
                        }
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
                    //
                } else {
                    timer.cancel();
                    LatLngBounds.Builder latLngBounds;
                    latLngBounds = new LatLngBounds.Builder();
                    for (Integer i = 0; i< lijstmarkers.size();i++) {
                                latLngBounds.include(lijstmarkers.get(i).locatie);
                    }
                    mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds.build(), 300));
                    afstandTotVolgendePuntTextView.setVisibility(View.INVISIBLE);
                    volgendePuntTextView.setVisibility(View.INVISIBLE);
                    PauzeButton.setVisibility(View.INVISIBLE);
                    relativeLayout.setVisibility(View.GONE);
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_root));
                    Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toastTekst = (TextView) toast.getView().findViewById(R.id.toast_text);
                    toastTekst.setText("Gefeliciteerd, de route is geslaagd!");
                    toast.show();
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
        polyline.setEndCap(new RoundCap());
        polyline.setStartCap(new RoundCap());
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
    //click event zodat de camera uitzoemt en niet meer volgt
    class PauzeButtonClick implements View.OnClickListener {
        public void onClick(View view) {
            if (!gepauzeerd) {
                gepauzeerd = true;
                PauzeButton.setText("Hervatten");
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(huidigeLocatie)
                        .zoom(14)
                        .build();
                mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            } else {
               if (couterMarkers != aantalMarkers) {
                   timer.start();
               }
                gepauzeerd = false;
                PauzeButton.setText("Bekijk");
            }
        }
    }
    //berekend opnimaal zoomLevel
    public int getBoundsZoomLevel(LatLng northeast,LatLng southwest,
                                       int width, int height) {
        double latFraction = (latRad(northeast.latitude) - latRad(southwest.latitude)) / Math.PI;
        double lngDiff = northeast.longitude - southwest.longitude;
        double lngFraction = ((lngDiff < 0) ? (lngDiff + 360) : lngDiff) / 360;
        double latZoom = zoom(height, GLOBE_WIDTH, latFraction);
        double lngZoom = zoom(width, GLOBE_WIDTH, lngFraction);
        double zoom = Math.min(Math.min(latZoom, lngZoom),ZOOM_MAX);
        return (int)(zoom);
    }
    private double latRad(double lat) {
        double sin = Math.sin(lat * Math.PI / 180);
        double radX2 = Math.log((1 + sin) / (1 - sin)) / 2;
        return Math.max(Math.min(radX2, Math.PI), -Math.PI) / 2;
    }
    private double zoom(double mapPx, double worldPx, double fraction) {
        final double LN2 = .693147180559945309417;
        return (Math.log(mapPx / worldPx / fraction) / LN2);
    }
}
