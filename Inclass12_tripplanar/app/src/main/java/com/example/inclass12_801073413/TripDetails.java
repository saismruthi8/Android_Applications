package com.example.inclass12_801073413;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class TripDetails extends AppCompatActivity {

    ListView lvtripdetails;
    PlacedetailsAdapter adapter;
    ArrayList<Placedetails> trips;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);
        FirebaseDatabase db;
        DatabaseReference ref;
        Placedetails obbj = new Placedetails();
//        obbj.setTripname("hello");
//        obbj.setDate("1/1/1");
//        obbj.setDestcity("char");

        trips =new ArrayList<>();
//        trips.add(obbj);
        lvtripdetails=findViewById(R.id.lvtripdetails);
        adapter=new PlacedetailsAdapter(TripDetails.this,R.layout.placedetailslayout,trips);
        lvtripdetails.setAdapter(adapter);
        adapter.setNotifyOnChange(true);
//        adapter.notifyDataSetChanged();
        if(getIntent() != null) {
            Log.d("Smu","Inside view trips");
        }
        else {
            Toast.makeText(getApplicationContext(), "Intent failed", android.widget.Toast.LENGTH_SHORT).show();
        }

        db = FirebaseDatabase.getInstance();
        ref = db.getReference("Places");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("smu", "on change working");
//                trips = new ArrayList<Placedetails>();

                for (DataSnapshot msgsnapshot : dataSnapshot.getChildren()) {
                    Placedetails mesg = new Placedetails();
                    try {
                        mesg = msgsnapshot.getValue(Placedetails.class);
                        trips.add(mesg);
                    } catch (Exception e) {
                        Log.d("smu", "crash " + e);
                    }
                }
                Log.d("smu", "trips list is" + trips.toString());
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        lvtripdetails.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Placedetails p=new Placedetails();
                p=adapter.getItem(position);
                Log.d("smu",""+p);
                Intent i=new Intent(TripDetails.this,MapActivity.class);
                i.putExtra("sel places",p);
                startActivity(i);

            }
        });
    }
}
