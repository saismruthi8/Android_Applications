package com.example.inclass12_801073413;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ArrayList<Trip> placeslist=new ArrayList<>();
    ArrayList<LatLng> loclist;
    ArrayList<String> placename;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        loclist = new ArrayList<>();
        placename = new ArrayList<>();


        if(getIntent() != null || getIntent().getExtras()!=null) {

            Placedetails p = (Placedetails) getIntent().getSerializableExtra("sel places");
            Log.d("Smu","Inside view trips");
            Log.d("smu",""+p);
            placeslist=p.getSelplaces();
            for(int i=0;i<placeslist.size();i++){
                Trip t=placeslist.get(i);
                loclist.add(new LatLng(Double.parseDouble(t.getLatitude()),Double.parseDouble(t.getLongitude())));
                placename.add(t.getName());
            }

        }
        else {
            Toast.makeText(getApplicationContext(), "Intent failed", android.widget.Toast.LENGTH_SHORT).show();
        }

        Log.d("smu",""+placeslist);
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        PolylineOptions polylineoptions;
        Polyline polyline=googleMap.addPolyline(new PolylineOptions()
                .addAll(loclist)
        );
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                LatLngBounds.Builder latlngboundbuilder=new LatLngBounds.Builder();

                for(LatLng p:loclist){
                    latlngboundbuilder.include(p);
                }
                LatLngBounds bounds=latlngboundbuilder.build();

                for(int i=0;i<placename.size();i++){
                    mMap.addMarker(new MarkerOptions().position(loclist.get(i)).title(placename.get(i)));
                }
                mMap.setLatLngBoundsForCameraTarget(bounds);
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latlngboundbuilder.build(),15));
            }
        });


    }
}
