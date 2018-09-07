package com.pawerskills.damianlzy.pawerapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.Objects;

public class splash_screen_white extends AppCompatActivity {
    public static final String SESSION = "login_status";
    public static final String SESSION_ID = "session";
    SharedPreferences sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //hide status bar before setting content view
    //        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
    //                WindowManager.LayoutParams.FLAG_FULLSCREEN);

    //
    //        setContentView(R.layout.activity_splash_screen_white);
//        startActivity(new Intent(splash_screen_white.this, sign_in.class));
//          close splash activity
//        finish();

        if(isNetworkAvailable()){

            long SPLASH_TIME_OUT = 1000;
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    sessionManager = getSharedPreferences(SESSION, Context.MODE_PRIVATE);
                    if (sessionManager.getString(SESSION_ID, null) == null) {
                        Intent i = new Intent(splash_screen_white.this, sign_in.class);
                        Log.w("SESSION_ID:", "not logged in. ID ->" + sessionManager.getString(SESSION_ID, null));
                        startActivity(i);
                        finish();
                    } else if (Objects.equals(sessionManager.getString(SESSION_ID, null), "200")) {
                        Intent i = new Intent(splash_screen_white.this, display_video.class);
                        Log.i("SESSION_ID ERROR:", "Logged in. ID ->" + sessionManager.getString(SESSION_ID, null));
                        startActivity(i);
                        finish();
                    } else {
                        Log.e("SESSION_ID ERROR:", "Retrieved_ID ->" + sessionManager.getString(SESSION_ID, null));
                    }
                }
            }, SPLASH_TIME_OUT);


        }else{
            Toast.makeText(this,"This App requires Internet. Please turn on your data and restart the app",Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
