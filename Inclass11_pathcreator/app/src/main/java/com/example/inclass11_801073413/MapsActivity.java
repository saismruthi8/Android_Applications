package com.example.inclass11_801073413;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ArrayList<LatLng> loclist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setLogo(R.mipmap.ic_launcher1);
        //getSupportActionBar().setDisplayUseLogoEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher1);
        setContentView(R.layout.activity_maps);
        setTitle("Paths Activity");

        //getActionBar().setIcon(R.drawable.ic_launcher);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        String json="";
        try{
            InputStream is=getAssets().open("trip.json");
            int size=is.available();
            byte[] buffer=new byte[size];
            is.read(buffer);
            is.close();
            json=new String(buffer,"UTF-8");
            Log.d("smu",json+"");

        }catch(IOException e)
        {
            e.printStackTrace();
        }
        Gson gson=new Gson();
        //Location loc=gson.fromJson(json,Location.class);
        Location loc=gson.fromJson(json,Location.class);
        ArrayList<Loc> locations=null;
        locations=loc.getListPoints();
        if(locations!=null)for(Loc point: locations){
            Log.d("latitude","Location:latitude"+point.getLatitude()+"");
        }

        loclist=new ArrayList<>();
        if(locations!=null)for(Loc item: locations){
            loclist.add(new LatLng(Double.parseDouble(item.getLatitude()),Double.parseDouble(item.getLongitude())));
        }

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

                mMap.addMarker(new MarkerOptions().position(loclist.get(0)).title("Start Location"));
                mMap.addMarker(new MarkerOptions().position(loclist.get(loclist.size()-1)).title("End Location"));
                mMap.setLatLngBoundsForCameraTarget(bounds);
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latlngboundbuilder.build(),15));
            }
        });


    }
}
