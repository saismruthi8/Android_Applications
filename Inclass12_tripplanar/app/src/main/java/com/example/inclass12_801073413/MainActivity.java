package com.example.inclass12_801073413;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inclass12_801073413.ListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    TextView destcity,tv_date;
    EditText searchcity,tripname;
    Spinner category;
    Button search,save,viewtrip;
    ListView lvplaces;
    ListAdapter adapter;
    ArrayList<City> citylist=new ArrayList<>();
    String categoryitem;
    ArrayList<Trip> placelist=new ArrayList<>();
    ArrayList<Trip> selectedplaces=new ArrayList<>();
    DatePickerDialog.OnDateSetListener mDateSetListener;
    FirebaseDatabase db;
    DatabaseReference ref;
    //FirebaseStorage storage;
    //StorageReference storageReference;
    String key,dName;
    ArrayList<Placedetails> messageList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        destcity = findViewById(R.id.btnsearch);
        tripname=findViewById(R.id.ettripname);
        searchcity = findViewById(R.id.etsearchcity);
        category = findViewById(R.id.spinner);
        search = findViewById(R.id.btnsearch);
        lvplaces = findViewById(R.id.lvplaces);
        tv_date = findViewById(R.id.tv_date);
        viewtrip=findViewById(R.id.btnviewtrip);
        save = findViewById(R.id.btnsave);
        db=FirebaseDatabase.getInstance();
        ref=db.getReference("Places");

        String[] categoryname = new String[]{"airports", "amusement parks", "aquarium", "car rental", "museum", "police station", "city hall", "parking"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, categoryname);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(adapter);


        tv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(MainActivity.this, android.R.style.Theme_Holo_Light_Dialog, mDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = month + "/" + dayOfMonth + "/" + year;
                tv_date.setText(date);
            }
        };


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String destinationcity = searchcity.getText().toString();
                categoryitem = category.getSelectedItem().toString();
                Log.d("smu category", categoryitem);
                if (isConnected()) {
                    new GetAPIAsync().execute("https://maps.googleapis.com/maps/api/place/findplacefromtext/json?input=" + destinationcity + "&inputtype=textquery&fields=name,geometry&key=AIzaSyBAjAacNM5mWpknMTQzswF0q6_VCC-ceCA&type=city_hall");
                } else {
                    Toast.makeText(MainActivity.this, "No Internet", Toast.LENGTH_SHORT).show();

                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("smu","inside save");
             Placedetails p=new Placedetails();

                 if(tripname.getText().length()==0|| tv_date.getText().length()==0|| searchcity.getText().length()==0){
                     Toast.makeText(getApplicationContext(),"Enter all details",Toast.LENGTH_LONG).show();
                 }
                 else {

                     p.setDate(tv_date.getText().toString());
                     p.setDestcity(searchcity.getText().toString());
                     p.setTripname(tripname.getText().toString());
                     p.setSelplaces(selectedplaces);
                     //messageList.add(p);
                     Log.d("db list", "" + p);
                     key = ref.push().getKey();
                     ref.child(key).setValue(p);
                     Toast.makeText(getApplicationContext(), "Details saved", Toast.LENGTH_SHORT).show();
                 }


            }

        });

        viewtrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,TripDetails.class);
                startActivity(i);
            }
        });



    }
    public class GetAPIAsync extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection = null;
            String result = null;
            try{
                String strUrl = strings[0];
                Log.d("url",""+strUrl);
                URL url = new URL(strUrl);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    result= IOUtils.toString(connection.getInputStream(),"UTF 8");
                    Log.d("inside do In",""+result);

                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally{
                if(connection!=null){
                    connection.disconnect();
                }
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {

            String latitude ="";
            String longitude ="" ;
            Log.d("smu","inside onpostexec");
            JSONObject root = null;
            Log.d("smu","inside onpostexec b4 try");
            try {
                Log.d("smu","inside try");
                root = new JSONObject(s);
                JSONArray candidates = root.getJSONArray("candidates");
                Log.d("smu","creating jsonarr");
               // for(int i =0; i<candidates.length();i++){
                    //articleList.clear();
                    JSONObject candidateJson1 = candidates.getJSONObject(0);
                Log.d("smu","obj for pos 0");
                     JSONObject geometry=candidateJson1.getJSONObject("geometry");
                    JSONObject location=geometry.getJSONObject("location");
                    latitude=location.getString("lat");
                    longitude=location.getString("lng");
                    Log.d("smu lat",latitude);
                    Log.d("smu long",longitude);
                    String cityname=candidateJson1.getString("name");

                    City cityobj=new City(latitude,longitude,cityname);
                    citylist.add(cityobj);
                    Log.d("inside postexecute",""+citylist);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(isConnected()){
              new GetPlacesAsync().execute("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+latitude+","+longitude+"&radius=15000&type="+categoryitem+"&key=AIzaSyBAjAacNM5mWpknMTQzswF0q6_VCC-ceCA");
            }else{
                Toast.makeText(MainActivity.this,"No Internet",Toast.LENGTH_SHORT).show();

            }

            super.onPostExecute(s);
        }
    }

    public class GetPlacesAsync extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... strings) {

            HttpURLConnection connection = null;
            String result = null;
            try{
                String strUrl = strings[0];
                Log.d("smu 2nd url",""+strUrl);
                URL url = new URL(strUrl);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    result= IOUtils.toString(connection.getInputStream(),"UTF 8");
                    Log.d("inside do In",""+result);

                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally{
                if(connection!=null){
                    connection.disconnect();
                }
            }
            return result;

        }

        @Override
        protected void onPostExecute(String s) {

            Log.d("smu","inside onpostexec");
            JSONObject root = null;
            Log.d("smu","inside onpostexec b4 try");
            try{
                Log.d("smu","inside try");
                root = new JSONObject(s);
                JSONArray results=root.getJSONArray("results");
                for(int i=0;i<results.length();i++){
                    Trip tripobj=new Trip();
                    JSONObject resultJson=results.getJSONObject(i);
                    JSONObject geometry=resultJson.getJSONObject("geometry");
                    JSONObject location=geometry.getJSONObject("location");
                    tripobj.latitude=location.getString("lat");
                    tripobj.longitude=location.getString("lng");
                    tripobj.name=resultJson.getString("name");

                    placelist.add(tripobj);
                    Log.d("inside postexecute",""+placelist);

                }

            }catch(JSONException e){
                e.printStackTrace();
            }

            adapter=new ListAdapter(MainActivity.this,R.layout.listviewlayout,placelist);
            lvplaces.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            lvplaces.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Trip trip=new Trip();
                    trip=adapter.getItem(position);
                    Log.d("smu",""+trip);
                    selectedplaces.add(trip);
                    Log.d("smu sel places",""+selectedplaces);

                }
            });


        }
    }


    private boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected() ||
                (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                        && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
            return false;
        }
        return true;
    }
}
