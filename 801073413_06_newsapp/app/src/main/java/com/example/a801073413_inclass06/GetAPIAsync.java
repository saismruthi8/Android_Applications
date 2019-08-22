package com.example.a801073413_inclass06;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class GetAPIAsync extends AsyncTask<String,Void,String> {

    IData idata;

    String keyword;
    public GetAPIAsync(IData iData) {
        this.idata = iData;
    }

    @Override
    protected void onPreExecute() {
        idata.preexecute();
        super.onPreExecute();
    }

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
        idata.handledata(s);
        Log.d("S:",""+s);
        //super.onPostExecute(s);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
          //idata.handledata();
        // super.onProgressUpdate(values);
    }

    public static interface  IData{
        public void handledata(String s);
        public void preexecute();
    }
}
