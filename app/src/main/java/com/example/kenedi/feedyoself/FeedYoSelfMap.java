package com.example.kenedi.feedyoself;

import android.*;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FeedYoSelfMap extends FragmentActivity implements
        OnMapReadyCallback, LocationListener {

    private static LatLng atwaterKent = new LatLng(42.275244, -71.806956);
    private static LatLng fullerLabs = new LatLng(42.274953, -71.806135);
    private static LatLng salisburyLabs = new LatLng(42.274497, -71.807011);
    private static LatLng campusCenter = new LatLng(42.274725, -71.808419);
    private static HashMap<String, LatLng> LOCATIONS = initializeLocationHashmap();


    private GoogleMap mMap;
    private static final long MIN_TIME = 400;
    private static final float MIN_DISTANCE = 1000;
    private LocationManager locationManager;
    private ArrayList<FoodEvent> foodEvents;

    private static HashMap<String, LatLng> initializeLocationHashmap() {
        HashMap<String, LatLng> locations = new HashMap<>();
        locations.put("Atwater Kent", atwaterKent);
        locations.put("Fuller Labs", fullerLabs);
        locations.put("Salisbury Labs", salisburyLabs);
        locations.put("Campus Center", campusCenter);
        return locations;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_yo_self_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //You can also use LocationManager.GPS_PROVIDER and LocationManager.PASSIVE_PROVIDER
//
//        // auto-generated permission
//        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            System.out.println("onCreate() permissions not set");
//            return;
//        }
//        System.out.println("request location updates");
//        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, this);

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

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(fullerLabs));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(16.5f));
        System.out.println("moved marker to fuller labs");
        foodEvents = (ArrayList<FoodEvent>) getIntent().getSerializableExtra("foodEvents");
        addEventMarkers(mMap, LOCATIONS, foodEvents);
    }

    private static void addEventMarkers(GoogleMap map, HashMap<String, LatLng> locations,
                                        ArrayList<FoodEvent> foodEvents)
    {
        for (FoodEvent foodEvent : foodEvents) {
            if (foodEvent.getLoc() != null && locations.containsKey(foodEvent.getLoc())){
                LatLng latlng = locations.get(foodEvent.getLoc());
                map.addMarker(new MarkerOptions().position(latlng).title(foodEvent.getTitle()));
            }

        }
    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10);
        mMap.animateCamera(cameraUpdate);

//        // auto-generated permission
//        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            System.out.println("onLocationChanged() permissions not set");
//            return;
//        }
//        System.out.println("remove updates");
//        locationManager.removeUpdates(this);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) { }

    @Override
    public void onProviderEnabled(String provider) { }

    @Override
    public void onProviderDisabled(String provider) { }

}
