package com.example.hp.qalightandroidapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SplashScreenActivity extends Activity {

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        if(isLoggedIn())
        {
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }else {
            intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

    }
    private boolean isLoggedIn(){
        // add here some code to check if user is logged in already

        return false;
    }

}
