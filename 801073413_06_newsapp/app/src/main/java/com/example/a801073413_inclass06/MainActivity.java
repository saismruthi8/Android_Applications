package com.example.a801073413_inclass06;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GetAPIAsync.IData {

    TextView tvcategory,tvtitle,tvpublished,tvdesc,tvpgno,tvload;
    ImageButton prev, next;
    Button go;
    ImageView ivurltoimg;
    ProgressBar prg;
    ArrayList<Article> articleList = new ArrayList<>();
    int temp=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Main Activity");
        tvcategory=findViewById(R.id.tvshowcategory);
        tvtitle=findViewById(R.id.tvtitle);
        tvpublished=findViewById(R.id.tvpublished);
        tvdesc=findViewById(R.id.tvdesc);
        tvpgno=findViewById(R.id.tvpgno);
        prev=findViewById(R.id.buttonprev);
        next=findViewById(R.id.buttonnext);
        prg=findViewById(R.id.progressBar);
        go=findViewById(R.id.buttongo);
        tvload=findViewById(R.id.tvload);
        ivurltoimg=findViewById(R.id.ivurl2image);
        prev.setVisibility(View.VISIBLE);
        next.setVisibility(View.VISIBLE);
        prev.setEnabled(false);
        next.setEnabled(false);
        tvtitle.setVisibility(View.INVISIBLE);
        tvpublished.setVisibility(View.INVISIBLE);
        tvdesc.setVisibility(View.INVISIBLE);
        ivurltoimg.setVisibility(View.INVISIBLE);
        tvpgno.setVisibility(View.INVISIBLE);
        prg.setVisibility(View.INVISIBLE);
        tvload.setVisibility(View.INVISIBLE);




        final String[] categoryarray={"business", "entertainment", "general","health", "science", "sports","technology"};

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Choose Category")
                        .setItems(categoryarray, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            String category=categoryarray[which];
                            tvcategory.setText(category);
                            prg.setVisibility(View.VISIBLE);
                            tvload.setVisibility(View.VISIBLE);

                            if(isConnected()){
                              new GetAPIAsync(MainActivity.this).execute("https://newsapi.org/v2/top-headlines?country=us&apiKey=d6ae062f917a485b97ed2e9900939f96&category="+category);
                            }else{
                                Toast.makeText(MainActivity.this,getString(R.string.no_internet),Toast.LENGTH_SHORT).show();

                            }
                            }
                        });

                builder.create().show();
                articleList.clear();
            }

           // builder.create().show();
        });
        //Article data=null;
        prev.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                //temp = temp - 1;
             if(temp>0){
             temp=temp-1;
                 if(isConnected()) {
                    Article data=articleList.get(temp);
                     Log.d("Inside",""+data);
                     tvtitle.setText(data.getTitle());
                     tvdesc.setText(data.getDescription());
                     tvpublished.setText(data.getPublishedAt());
                     ivurltoimg.setVisibility(View.VISIBLE);
                     tvtitle.setVisibility(View.VISIBLE);
                     tvpublished.setVisibility(View.VISIBLE);
                     tvdesc.setVisibility(View.VISIBLE);
                     Picasso.get().load(data.getUrl()).into(ivurltoimg);
                     //prev.setVisibility(View.VISIBLE);
                     //next.setVisibility(View.VISIBLE);
                     tvpgno.setVisibility(View.VISIBLE);
                     prg.setVisibility(View.INVISIBLE);
                     tvload.setVisibility(View.INVISIBLE);

                 }else{
                     Toast.makeText(MainActivity.this,getString(R.string.no_internet),Toast.LENGTH_SHORT).show();
                 }} else if(temp==0){
                      temp=articleList.size()-1;
                 if(isConnected()) {
                     Article data=articleList.get(temp);
                     tvtitle.setText(data.getTitle());
                     tvdesc.setText(data.getDescription());
                     tvpublished.setText(data.getPublishedAt());
                     ivurltoimg.setVisibility(View.VISIBLE);
                     tvtitle.setVisibility(View.VISIBLE);
                     tvpublished.setVisibility(View.VISIBLE);
                     tvdesc.setVisibility(View.VISIBLE);
                     Picasso.get().load(data.getUrl()).into(ivurltoimg);
                     //prev.setVisibility(View.VISIBLE);
                     //next.setVisibility(View.VISIBLE);
                     tvpgno.setVisibility(View.VISIBLE);
                     prg.setVisibility(View.INVISIBLE);
                     tvload.setVisibility(View.INVISIBLE);
                 }else{
                     Toast.makeText(MainActivity.this,getString(R.string.no_internet),Toast.LENGTH_SHORT).show();
                 }
                 }
                 tvpgno.setText(String.valueOf(temp+1)+" out of "+String.valueOf(articleList.size()));

            }
        });

        next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("inside next",".....");


             if(temp<articleList.size()-1){
                 Log.d("demo","inside first if");
                 temp = temp + 1;
                 //if(isConnected()) {
                     Article data=articleList.get(temp);
                     Log.d("Inside",""+data);
                     tvtitle.setText(data.getTitle());
                     tvdesc.setText(data.getDescription());
                     tvpublished.setText(data.getPublishedAt());
                     ivurltoimg.setVisibility(View.VISIBLE);
                     tvtitle.setVisibility(View.VISIBLE);
                     tvpublished.setVisibility(View.VISIBLE);
                     tvdesc.setVisibility(View.VISIBLE);
                 Picasso.get().load(data.getUrl()).into(ivurltoimg);
                     //prev.setVisibility(View.VISIBLE);
                     //next.setVisibility(View.VISIBLE);
                     tvpgno.setVisibility(View.VISIBLE);
                     prg.setVisibility(View.INVISIBLE);
                 tvload.setVisibility(View.INVISIBLE);

                 /*}else{
                     Toast.makeText(MainActivity.this,getString(R.string.no_internet),Toast.LENGTH_SHORT).show();
                 }*/
             } else if(temp==(articleList.size()-1)){
                 temp=0;
                 //if(isConnected()) {
                     Article data=articleList.get(temp);
                     tvtitle.setText(data.getTitle());
                     tvdesc.setText(data.getDescription());
                     tvpublished.setText(data.getPublishedAt());
                     ivurltoimg.setVisibility(View.VISIBLE);
                     tvtitle.setVisibility(View.VISIBLE);
                     tvpublished.setVisibility(View.VISIBLE);
                     tvdesc.setVisibility(View.VISIBLE);
                 Picasso.get().load(data.getUrl()).into(ivurltoimg);
                     //prev.setVisibility(View.VISIBLE);
                     //next.setVisibility(View.VISIBLE);
                     tvpgno.setVisibility(View.VISIBLE);
                     prg.setVisibility(View.INVISIBLE);
                     tvload.setVisibility(View.INVISIBLE);
                 /*}else{
                     Toast.makeText(MainActivity.this,getString(R.string.no_internet),Toast.LENGTH_SHORT).show();
                 }*/
             }
             tvpgno.setText(String.valueOf(temp+1)+" out of "+String.valueOf(articleList.size()));
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

        JSONObject root = null;
        try {
            root = new JSONObject(s);
            JSONArray articles = root.getJSONArray("articles");
            for(int i =0; i<articles.length();i++){
                //articleList.clear();
                JSONObject articleJson = articles.getJSONObject(i);
                String title = articleJson.getString("title");
                String description = articleJson.getString("description");
                String publishedAt = articleJson.getString("publishedAt");
                String url = articleJson.getString("urlToImage");
                Article article = new Article(title,description,publishedAt,url);
                articleList.add(article);
                Log.d("inside for..",""+articleList);
            } Log.d("articles",""+articleList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(articleList.size()==1){
            prev.setEnabled(false);
            next.setEnabled(false);
            ivurltoimg.setVisibility(View.VISIBLE);
            tvtitle.setVisibility(View.VISIBLE);
            tvpublished.setVisibility(View.VISIBLE);
            tvdesc.setVisibility(View.VISIBLE);

            prev.setVisibility(View.VISIBLE);
            next.setVisibility(View.VISIBLE);
        }
        else if(articleList.size()==0){
            Toast.makeText(MainActivity.this, "No news found", Toast.LENGTH_SHORT).show();
            ivurltoimg.setVisibility(View.INVISIBLE);
            tvtitle.setVisibility(View.INVISIBLE);
            tvpublished.setVisibility(View.INVISIBLE);
            tvdesc.setVisibility(View.INVISIBLE);

            prev.setVisibility(View.INVISIBLE);
            next.setVisibility(View.INVISIBLE);
        } else{
            prev.setEnabled(true);
            next.setEnabled(true);
            ivurltoimg.setVisibility(View.VISIBLE);
            tvtitle.setVisibility(View.VISIBLE);
            tvpublished.setVisibility(View.VISIBLE);
            tvdesc.setVisibility(View.VISIBLE);

            prev.setVisibility(View.VISIBLE);
            next.setVisibility(View.VISIBLE);
        }
        Article data = articleList.get(temp);
        tvtitle.setText(data.getTitle());
        tvdesc.setText(data.getDescription());
        tvpublished.setText(data.getPublishedAt());
        Picasso.get().load(data.getUrl()).into(ivurltoimg);
        tvpgno.setText(String.valueOf(temp+1)+" out of "+String.valueOf(articleList.size()));

        tvpgno.setVisibility(View.VISIBLE);
        prg.setVisibility(View.INVISIBLE);
        tvload.setVisibility(View.INVISIBLE);


    }

    @Override
    public void preexecute() {
        prg.setVisibility(View.VISIBLE);
        tvload.setVisibility(View.VISIBLE);
        ivurltoimg.setVisibility(View.INVISIBLE);
        tvtitle.setVisibility(View.INVISIBLE);
        tvpublished.setVisibility(View.INVISIBLE);
        tvdesc.setVisibility(View.INVISIBLE);
        prev.setVisibility(View.INVISIBLE);
        next.setVisibility(View.INVISIBLE);
        tvpgno.setVisibility(View.INVISIBLE);

    }


}
