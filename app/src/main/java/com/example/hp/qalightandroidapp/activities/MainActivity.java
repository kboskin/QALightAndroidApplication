package com.example.hp.qalightandroidapp.activities;

import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.example.hp.qalightandroidapp.R;
import com.example.hp.qalightandroidapp.fragments.aboutus.AboutUsFragment;
import com.example.hp.qalightandroidapp.fragments.calendar.CalendarFragment;
import com.example.hp.qalightandroidapp.fragments.materialsandtests.FixturesTabsFragment;
import com.example.hp.qalightandroidapp.fragments.motivations.MotivationsFragment;
import com.example.hp.qalightandroidapp.fragments.payment.PaymentFragment;
import com.judopay.Judo;
import com.judopay.model.Currency;

import static com.example.hp.qalightandroidapp.Constants.CHECK_IF_IS_AUTH_PASSED;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private SharedPreferences prefs;
    private Intent backToLoginIntent;

    private FixturesTabsFragment fixturesTabsFragment;
    private CalendarFragment calendarFragment;
    private MotivationsFragment motivationsFragment;
    private AboutUsFragment aboutUsFragment;
    private PaymentFragment paymentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        // set color of status bar
        setStatusBarColor();

        // select default fragment
        setDefaultFragment(savedInstanceState);


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_calendar)
        {
            calendarFragment = new CalendarFragment();
            replaceWithFragment(calendarFragment);

        } else if (id == R.id.nav_materials_and_tests) {
            fixturesTabsFragment = new FixturesTabsFragment();
            replaceWithFragment(fixturesTabsFragment);
        } else if (id == R.id.nav_notifications) {

        } else if (id == R.id.nav_payment) {
            /*Intent i = new Intent(MainActivity.this, PaymentActivity.class);
            startActivity(i);*/
            Bundle bundle = new Bundle();
            bundle.putParcelable(Judo.JUDO_OPTIONS, new Judo.Builder()
                    .setApiToken("miDYdMicrvRrdH3X")
                    .setApiSecret("58035c366cb500fdd3a086abcec4a89e4ce9b4552f5616c25356f8ef59dee907")
                    .setJudoId("100474-121")
                    .setEnvironment(Judo.SANDBOX)
                    .setAmount("2.00")
                    .setCurrency(Currency.GBP)
                    //.setConsumerReference("<CONSUMER_REFERENCE>")
                    .build());

            com.judopay.PaymentFragment fragment = new com.judopay.PaymentFragment();
            fragment.setArguments(bundle);

            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.frgmCont, fragment)
                    .commit();


        } else if (id == R.id.nav_motivation) {
            motivationsFragment = new MotivationsFragment();
            replaceWithFragment(motivationsFragment);
        }
        else if(id == R.id.nav_aboutus)
        {
            aboutUsFragment = new AboutUsFragment();
            replaceWithFragment(aboutUsFragment);
        }
        else if(id == R.id.nav_exit)
        {
            rewriteLogInValueAndBackToLogIn();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Judo.JUDO_REQUEST) {
            switch (resultCode) {
                case Judo.RESULT_SUCCESS:
                    // handle success
                    break;

                case Judo.RESULT_DECLINED:
                    // handle declined
                    break;
            }
        }
    }
    private void rewriteLogInValueAndBackToLogIn()
    {
        // change value of our variable
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(CHECK_IF_IS_AUTH_PASSED, false);
        editor.apply();

        // back to LoginActivity
        backToLoginIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(backToLoginIntent);
        finish();
    }
    private void setStatusBarColor()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }
    private void replaceWithFragment(Fragment fragment)
    {
        // frgmcont has strong reference because we always replace it exactly
        getSupportFragmentManager().beginTransaction().replace(R.id.frgmCont, fragment).commit();
    }
    private void setDefaultFragment(Bundle savedInstanceState)
    {
        if (savedInstanceState == null)
        {
            calendarFragment = new CalendarFragment();
            replaceWithFragment(calendarFragment);
        }
    }

}
