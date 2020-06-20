package com.example.memorableplaces;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    Intent intent;
    LocationManager locationManager;
    LocationListener locationListener;
    int lat;
    int lng;
    LatLng userloc;
    ArrayList<String> cakelist;
    ArrayList<String> cakelatlist;
    ArrayList<String> cakelnglist;





    private GoogleMap mMap;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        intent = getIntent();




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
        cakelist = intent.getStringArrayListExtra("ARRAYOFSTUFF");
        cakelatlist = intent.getStringArrayListExtra("ARRAYOFCOORDLAT");
        cakelnglist = intent.getStringArrayListExtra("ARRAYOFCOORDLNG");




        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                try {
                    List<Address> listaddresses = geocoder.getFromLocation(latLng.latitude,latLng.longitude,1);
                    mMap.addMarker(new MarkerOptions().position(latLng).title(listaddresses.get(0).getPostalCode()+" "+ listaddresses.get(0).getLocality()));
                    intent.putExtra("CITYPLUSCODE", listaddresses.get(0).getCountryName()+" "+ listaddresses.get(0).getPostalCode());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                intent.putExtra("FIRSTTIME", "1");
                intent.putExtra("ARRAYOFSTUFF", cakelist);
                intent.putExtra("ARRAYOFCOORDLAT", cakelatlist);
                intent.putExtra("ARRAYOFCOORDLNG", cakelnglist);

                intent.putExtra("LAT", Double.toString(latLng.latitude));
                intent.putExtra("LNG", Double.toString(latLng.longitude));
                startActivity(intent);




            }
        });


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if(intent.getStringExtra("MARKING").compareTo("marking") == 0) {
                    userloc = new LatLng(location.getLatitude(), location.getLongitude());


                }
                else{
                    userloc = new LatLng(Double.parseDouble(intent.getStringExtra("LAT")),Double.parseDouble(intent.getStringExtra("LNG")));

                }
                mMap.moveCamera(CameraUpdateFactory.newLatLng(userloc));
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };





        // Add a marker in Sydney and move the camera
        /*userloc = new LatLng(lat, lng);
        mMap.addMarker(new MarkerOptions().position(userloc).title("Your location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userloc, 16.0f));

         */


        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        else{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0, 0, locationListener);
            if(intent.getStringExtra("MARKING").compareTo("marking") == 0) {
                Location lastknowloc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                userloc = new LatLng(lastknowloc.getLatitude(), lastknowloc.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userloc, 16f));

            }
            else{
                userloc = new LatLng(Double.parseDouble(intent.getStringExtra("LAT")),Double.parseDouble(intent.getStringExtra("LNG")));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userloc, 16f));
                mMap.addMarker(new MarkerOptions().position(userloc).title("Memorable Location"));
            }
        }

    }
}
