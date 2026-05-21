package com.example.weather_java;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.androidnetworking.AndroidNetworking;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity {

    Toolbar tlbr;
    LottieAnimationView ltv;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    TextView txt, la, lo;
    double latitude, longitude;
    private LocationCallback locationCallback;
    private FusedLocationProviderClient fusedLocationProviderClient;
    ImageView icon;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tlbr = findViewById(R.id.tlbr);

        txt = findViewById(R.id.error);

        ltv=findViewById(R.id.lottie);

        setSupportActionBar(tlbr);
        getSupportActionBar().setTitle("Mausam");
        ltv.playAnimation();

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        AndroidNetworking.initialize(getApplicationContext());

        // Ask permission and start location
        requestLocationPermission();
    }

    private void requestLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            startLocationUpdates();
        }
    }

    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(2000);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                if (locationResult == null) return;

                for (Location location : locationResult.getLocations()) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();


                    txt.setText("Latitude: " + latitude + "\nLongitude: " + longitude);

                    // Start WeatherDetails once valid coordinates are received
                    if (latitude != 0 && longitude != 0) {
                        Intent weatherIntent = new Intent(MainActivity.this, WeatherDetails.class);
                        weatherIntent.putExtra("latitude", String.valueOf(latitude));
                        weatherIntent.putExtra("longitude", String.valueOf(longitude));
                        startActivity(weatherIntent);
                        finish();

                        // Stop location updates to save battery
                        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
                        break;
                    }
                }
            }
        };

        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, getMainLooper());
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (fusedLocationProviderClient != null && locationCallback != null) {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates();
            } else {
                Toast.makeText(this, "Location permission is required", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
