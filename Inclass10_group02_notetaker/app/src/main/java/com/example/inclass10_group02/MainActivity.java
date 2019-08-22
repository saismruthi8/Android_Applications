package com.example.inclass10_group02;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    EditText email,password;
    Button login,signup;
    String emailid,pwd;
    boolean isValid = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Login");
        email = findViewById(R.id.etemail);
        password = findViewById(R.id.etpwd);

        login = findViewById(R.id.buttonlogin);
        signup = findViewById(R.id.buttonsignup);
        OkHttpClient client = new OkHttpClient();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnected(getSystemService(Context.CONNECTIVITY_SERVICE))) {


                    emailid = email.getText().toString();
                    Log.d("Demo", "email" + email);
                    pwd = password.getText().toString();


                    if (emailid == null || emailid.equals("") || emailid.trim().equals("")) {
                        email.setError("Enter Email");
                        isValid = false;
                    } else if (pwd == null || pwd.equals("") || pwd.trim().equals("")) {
                        password.setError("Enter Password");
                        isValid = false;
                    }

                    if (isValid) {
                        OkHttpClient client = new OkHttpClient();

                        RequestBody formBody = new FormBody.Builder()
                                .add("email", emailid)
                                .add("password", pwd)
                                .build();
                        Request request = new Request.Builder()
                                .url("http://ec2-3-91-77-16.compute-1.amazonaws.com:3000/api/auth/login")
                                .post(formBody)
                                .build();


                        client.newCall(request).enqueue(new Callback() {

                            @Override
                            public void onFailure(Call call, IOException e) {
                                e.printStackTrace();

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                try {
                                    String js=response.body().string();
                                    JSONObject res=new JSONObject(js);
                                    String isauth=res.getString("auth");
                                    String token=res.getString("token");
                                    Log.d("smu","auth"+isauth);
                                    Log.d("smu","token"+token);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                    }

                }

            }


        });
    }

    public static boolean isConnected(Object systemService ) {
        Log.d("demo", "START: isConnected");
        ConnectivityManager connectivityManager = (ConnectivityManager)  systemService;
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected() || (networkInfo.getType() != ConnectivityManager.TYPE_WIFI && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
            return false;
        }
        Log.d("demo", "END: isConnected is " + true);
        return true;
    }
}
