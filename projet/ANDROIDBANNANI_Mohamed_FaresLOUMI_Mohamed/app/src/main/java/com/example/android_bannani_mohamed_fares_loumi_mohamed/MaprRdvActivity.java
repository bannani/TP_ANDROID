package com.example.android_bannani_mohamed_fares_loumi_mohamed;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.example.android_bannani_mohamed_fares_loumi_mohamed.domain.Rdv;
import com.example.android_bannani_mohamed_fares_loumi_mohamed.repository.MySqlLiteDB;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class MaprRdvActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap gMap;
    private Button buttonReturn;
    private String name;
    private String localisation;
    private MySqlLiteDB db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_rdv);
        db=new MySqlLiteDB(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        buttonReturn = (Button) findViewById(R.id.buttonDone);
        name = getIntent().getExtras().getString("username");
        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MaprRdvActivity.this, MyAccountActivity.class);
                intent.putExtra("username", name);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        List<Rdv> rdvs=db.readALLRdv();
        Log.d("taille rdv", String.valueOf(rdvs.size()));
        gMap = googleMap;
        int i=0;
        for (Rdv rdv : rdvs)
        {
            i++;
            LatLng latLng=new LatLng(rdv.getLongitude(),rdv.getLatitude());
            MarkerOptions markerOption=new MarkerOptions();
            markerOption.position(latLng);
            markerOption.title(i+": "+rdv.getUsername()+" , "+rdv.getDate());
            gMap.addMarker(markerOption);
            Log.d("taille rdv", rdv.getUsername()+","+rdv.getDate());

        }


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