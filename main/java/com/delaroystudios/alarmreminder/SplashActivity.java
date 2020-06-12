package com.delaroystudios.alarmreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.delaroystudios.alarmreminder.data.Constants;

public class SplashActivity extends AppCompatActivity {
    HealthPreference s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        s=new HealthPreference(getApplicationContext());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i;
                if (s.checkLogin())
                {
                    i=new Intent(SplashActivity.this,HomeActivity.class);
                }
                else {
                    i = new Intent(SplashActivity.this, LoginActivity.class);
                }
                startActivity(i);
                finish();
            }
        },2000);
    }
}
