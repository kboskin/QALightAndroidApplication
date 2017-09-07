package com.example.hp.qalightandroidapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.Window;
import android.view.WindowManager;

import static com.example.hp.qalightandroidapp.Constants.*;

public class SplashScreenActivity extends Activity {

    private Intent intent;
    private SharedPreferences prefs;

    // migrated to onStart method, because it runs earlier
    // then onCreate(). So it will make app more productive

    @Override
    protected void onStart() {
        super.onStart();

        // lines makes activity to become full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

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
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        return prefs.getBoolean(CHECK_IF_IS_AUTH_PASSED, false);
    }


}
