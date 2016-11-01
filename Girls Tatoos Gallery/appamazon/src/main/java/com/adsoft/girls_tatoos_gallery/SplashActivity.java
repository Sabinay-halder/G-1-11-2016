package com.adsoft.girls_tatoos_gallery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


/**
 * Created by mercury-four on 18/11/15.
 */
public class SplashActivity extends Activity{

    int SPLASH_TIME_OUT = 5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activily_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                Intent i = new Intent(SplashActivity.this, HorizontalLayoutFragment.class);
                startActivity(i);
                finish();


            }
        }, SPLASH_TIME_OUT);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



}
