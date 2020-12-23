package com.example.android_bannani_mohamed_fares_loumi_mohamed;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap gMap;
    private Button buttonReturn;
    private String name;
    private String localisation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        buttonReturn = (Button) findViewById(R.id.buttonDone);
        name = getIntent().getExtras().getString("username");
        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapActivity.this, NewRDVActivity.class);
                intent.putExtra("localisation", localisation);
                intent.putExtra("username", name);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        gMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                MarkerOptions markerOption=new MarkerOptions();
                markerOption.position(latLng);
                localisation=latLng.latitude+ ","+latLng.longitude;
                markerOption.title(latLng.latitude+ ":"+latLng.longitude);
                gMap.clear();
                gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
                gMap.addMarker(markerOption);

            }
        });


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        gMap.setMyLocationEnabled(true);
    }
}
