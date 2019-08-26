package com.example.hw3_grp3;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import static android.view.View.INVISIBLE;

public class MainActivity extends AppCompatActivity implements GetAPIAsync.IData {

    TextView tvlimit,tvlimitvalue,tvsortby,tvresult,tvloading,tvsortprice,tvsortdate;
    EditText etsearch;
    Button search,reset;
    SeekBar seeklimit;
    Switch sort;
    ListView lvmusicresults;
    ProgressBar progressBar;
    String keyword;
    String limitvalue;
    ArrayList<Results> resultlist=new ArrayList<>();
    static String resultkey = "results";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("iTunes Music Search");
        tvlimit=findViewById(R.id.tvlimit);
        tvlimitvalue=findViewById(R.id.tvlimitvalue);
        tvlimitvalue.setText(String.valueOf(10));
        tvsortby=findViewById(R.id.tvsortby);
        tvresult=findViewById(R.id.tvresult);
        etsearch=findViewById(R.id.edittextsearch);
        search=findViewById(R.id.buttonsearch);
        reset=findViewById(R.id.buttonreset);
        seeklimit=findViewById(R.id.seekBarlimit);
        sort=findViewById(R.id.switch1);
        sort.setChecked(true);
        tvsortprice=findViewById(R.id.tvsortprice);
        tvsortdate=findViewById(R.id.tvsortdate);
        lvmusicresults=findViewById(R.id.lvmusiclist);
        lvmusicresults.setVisibility(View.VISIBLE);
        seeklimit.setProgress(10);// by default, it'll be pointing here
        progressBar=findViewById(R.id.progressBar);
        progressBar.setVisibility(INVISIBLE);
        tvloading=findViewById(R.id.tvloading);
        tvloading.setVisibility(INVISIBLE);



        seeklimit.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progress<10){
                    Toast.makeText(MainActivity.this,"value can't be lesser than 10",Toast.LENGTH_SHORT).show();
                }
                limitvalue=String.valueOf(progress);
                tvlimitvalue.setText(limitvalue);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyword=etsearch.getText().toString().replace(" ","+");
                Log.d("keyword",keyword);
                Log.d("limit",limitvalue);
                if(isConnected()){
                    if(keyword.isEmpty()){
                        Toast.makeText(MainActivity.this,"Enter search keyword",Toast.LENGTH_SHORT).show();
                    }
                    else{
                    new GetAPIAsync(MainActivity.this).execute("https://itunes.apple.com/search?term="+keyword+"&limit="+limitvalue);
                }}else{
                    Toast.makeText(MainActivity.this,getString(R.string.no_internet),Toast.LENGTH_SHORT).show();

                }
                resultlist.clear();
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etsearch.setText(" ");
                keyword=" ";
                seeklimit.setProgress(10);
                tvlimitvalue.setText(String.valueOf(10));
                lvmusicresults.setAdapter(null);//clears listview
                sort.setChecked(true);
            }
        });

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

    @Override
    public void handledata(String s) {
        Log.d("inside handledata",s);
        JSONObject root = null;
        try {
            root = new JSONObject(s);
            JSONArray results = root.getJSONArray("results");

            for(int i =0; i<results.length();i++){
                //articleList.clear();
                Log.d("Inside for","json");
                Results res=new Results(); // every time, new obj has to be created! else everything will reference the same obj so same values get displayed many times
                JSONObject resultJson = results.getJSONObject(i);
                res.trackname = resultJson.getString("trackName");
                res.genre = resultJson.getString("primaryGenreName");
                res.artist = resultJson.getString("artistName");
                res.album = resultJson.getString("collectionName");
                res.trackprice=resultJson.getString("trackPrice");
                res.albumprice=resultJson.getString("collectionPrice");

    DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
    Date date = dateFormat.parse(resultJson.getString("releaseDate"));//You will get date object relative to server/client timezone wherever it is parsed
    DateFormat formatter = new SimpleDateFormat("YYYY-MM-dd"); //If you need time just put specific format for time like 'HH:mm:ss'
    String dateStr = formatter.format(date);
    res.date = dateStr;





               // res.date=resultJson.getString("releaseDate");
                res.urltoimg=resultJson.getString("artworkUrl100");
                Log.d("list",""+resultlist);
                resultlist.add(res);
                Log.d("inside for..",""+resultlist);
            } Log.d("resultssss",""+resultlist);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(sort.isChecked() && resultlist!=null)
            Collections.sort(resultlist, Results.Comparators.DATE);
        else
            Collections.sort(resultlist, Results.Comparators.PRICE);
        final ResultAdapter adapter=new ResultAdapter(this,R.layout.listview_layout,resultlist);
        lvmusicresults.setAdapter(adapter);
        adapter.setNotifyOnChange(true);

        lvmusicresults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Results res=new Results();
                res= adapter.getItem(position);
                Intent i=new Intent(MainActivity.this,Display_details.class);
                i.putExtra(resultkey,res);
                startActivity(i);
            }
        });

    }

    @Override
    public void preexecute() {
        for(int i=0;i<10000;i++){
            for(int j=0;j<1000;j++){
        }}
     progressBar.setVisibility(View.VISIBLE);
     tvloading.setVisibility(View.VISIBLE);
     etsearch.setVisibility(INVISIBLE);
     tvlimit.setVisibility(INVISIBLE);
     tvlimitvalue.setVisibility(INVISIBLE);
     search.setVisibility(INVISIBLE);
     reset.setVisibility(INVISIBLE);
     tvsortby.setVisibility(INVISIBLE);
      sort.setVisibility(INVISIBLE);
      tvresult.setVisibility(INVISIBLE);
      tvsortprice.setVisibility(INVISIBLE);
      tvsortdate.setVisibility(INVISIBLE);



    }
}
