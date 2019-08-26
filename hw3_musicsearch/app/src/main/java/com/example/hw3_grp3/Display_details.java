package com.example.hw3_grp3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Display_details extends AppCompatActivity {

    TextView tvtrack,tvtrackname,tvgenre,tvgenrename,tvartist,tvartistname,tvalbum,tvalbumname,tvtrackprice,tvtrackpricename,tvalbumprice,tvalbumpriceval;
    Button finish;
    ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_details);
        setTitle("iTunes Music Search");
        tvtrackname=findViewById(R.id.tvtracktitle);
        tvartistname=findViewById(R.id.tvartistname);
        tvalbumname=findViewById(R.id.tvalbumname);
        tvtrackpricename=findViewById(R.id.tvtrackpriceval);
        tvalbumpriceval=findViewById(R.id.tvalbumpriceval);
        tvgenrename=findViewById(R.id.tvgenreval);
        finish=findViewById(R.id.buttonfinish);
        iv=findViewById(R.id.ivmusicimg);



        if (getIntent() != null && getIntent().getExtras() != null) {
           Results result = (Results) getIntent().getExtras().getSerializable(MainActivity.resultkey);
            tvtrackname.setText(result.trackname);
             tvgenrename.setText(result.genre);
            tvartistname.setText(result.artist);
            tvalbumname.setText(result.album);
            tvtrackpricename.setText(result.trackprice+"$");
            tvalbumpriceval.setText(result.albumprice+"$");
            Picasso.get().load(result.urltoimg).resize(100,100).into(iv);
            Log.d("demo","error");
        }
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
