package com.m2dl.projetandroid.projetandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import java.io.File;

public class SplashScreen extends Activity {

    // Splash screen timer
    private int SPLASH_TIME_OUT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        File f=getFileStreamPath("userinfos.xml");

        if (f.exists()) {
            SPLASH_TIME_OUT = 3000;
            setContentView(R.layout.loadinglayout);
        } else {
            SPLASH_TIME_OUT = 0;
        }

        new Handler().postDelayed(new Runnable() {
 
            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}