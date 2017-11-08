package com.example.hp.qalightandroidapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.hp.qalightandroidapp.R;
import com.example.hp.qalightandroidapp.fragments.aboutus.AboutUsFragment;
import com.example.hp.qalightandroidapp.fragments.calendar.CalendarFragment;
import com.example.hp.qalightandroidapp.fragments.materialsandtests.FixturesTabsFragment;
import com.example.hp.qalightandroidapp.fragments.motivations.MotivationsFragment;
import com.example.hp.qalightandroidapp.fragments.notifications.NotificationsFragment;
import com.example.hp.qalightandroidapp.fragments.payment.PaymentFragment;

import static com.example.hp.qalightandroidapp.Constants.CHECK_IF_IS_AUTH_PASSED;
import static com.example.hp.qalightandroidapp.Constants.EXTRA_NOTIFICATION_FRAGMENT;
import static com.example.hp.qalightandroidapp.Constants.HELLO_MESSAGE_FOR_USER;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private SharedPreferences prefs;
    private FixturesTabsFragment fixturesTabsFragment;
    private CalendarFragment calendarFragment;
    private MotivationsFragment motivationsFragment;
    private AboutUsFragment aboutUsFragment;
    private PaymentFragment paymentFragment;
    private NotificationsFragment notificationFragment;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        // set color of status bar
        setStatusBarColor();

        // select default fragment
        setDefaultFragment(savedInstanceState);
        // denied orientation changing
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        // get header
        View headerLayout = navigationView.getHeaderView(0);

        // set hello message
        TextView groupText = headerLayout.findViewById(R.id.group_text);
        groupText.setText(getResources().getString(R.string.hello) + " " + prefs.getString(HELLO_MESSAGE_FOR_USER, ""));

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkForNotificationsExtra(getIntent().getStringExtra(EXTRA_NOTIFICATION_FRAGMENT));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);

        // handler to handle android navigation lag
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        drawer.closeDrawer(Gravity.LEFT, true);
                    }
                }, 200);
                return false;
            }
        });

        // Handle navigation view item clicks here.
        initializeDrawerItemList(item);

        return true;
    }


    private void rewriteLogInValueAndBackToLogIn() {
        // change value of our variable
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(CHECK_IF_IS_AUTH_PASSED, false);
        editor.apply();

        // back to LoginActivity
        Intent backToLoginIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(backToLoginIntent);
        finish();
    }

    private void setStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    private void replaceWithFragment(Fragment fragment, Handler handler) {
        // frgmcont has strong reference because we always replace it exactly
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frgmCont, fragment)
                .addToBackStack(String.valueOf(fragment.getId()))
                .commit();

        handler.sendEmptyMessage(1);

    }

    private void replaceWithFragment(Fragment fragment) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frgmCont, fragment)
                .addToBackStack(String.valueOf(fragment.getId()))
                .commit();

    }

    private void setDefaultFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            calendarFragment = new CalendarFragment();
            replaceWithFragment(calendarFragment);
        }
    }

    private void initializeDrawerItemList(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_calendar:
                if (calendarFragment == null) {
                    calendarFragment = new CalendarFragment();
                }
                replaceWithFragment(calendarFragment, handler);
                break;
            case R.id.nav_materials_and_tests:
                if (fixturesTabsFragment == null) {
                    fixturesTabsFragment = new FixturesTabsFragment();
                }
                replaceWithFragment(fixturesTabsFragment, handler);
                break;
            case R.id.nav_notifications:
                if (notificationFragment == null) {

                    notificationFragment = new NotificationsFragment();
                }
                replaceWithFragment(notificationFragment, handler);
                break;
            case R.id.nav_payment:
                break;
            case R.id.nav_motivation:
                if (motivationsFragment == null) {
                    motivationsFragment = new MotivationsFragment();
                }
                replaceWithFragment(motivationsFragment, handler);
                break;
            case R.id.nav_aboutus:
                if (aboutUsFragment == null) {
                    aboutUsFragment = new AboutUsFragment();
                }
                replaceWithFragment(aboutUsFragment, handler);
                break;
            case R.id.nav_exit:
                rewriteLogInValueAndBackToLogIn();
                break;
        }
    }

    // put the notifications fragment as main when opening notification
    private void checkForNotificationsExtra(String notificationExtra) {
        if (notificationExtra != null) {

            // Here we can decide what do to -- perhaps load other parameters from the intent extras such as IDs, etc
            if (notificationExtra.equals("openIt")) {
                notificationFragment = new NotificationsFragment();
                replaceWithFragment(notificationFragment, handler);
            }
        } else {
            // Activity was not launched with a menuFragment selected -- continue as if this activity was opened from a launcher (for example)
            calendarFragment = new CalendarFragment();
            replaceWithFragment(calendarFragment);
        }
    }


}
