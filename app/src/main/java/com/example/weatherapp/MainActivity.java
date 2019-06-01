package com.example.weatherapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_CITY_NAME = "CityName";

    NavigationView navigationView;
    FragmentManager fragmentManager;
    SharedPreferences mSettings;
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initDrawer(toolbar);
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        fragmentManager = getSupportFragmentManager();
        initStartFragment();


    }

    private void initStartFragment() {
        if(mSettings.contains(APP_CITY_NAME)){
            fragment = new WetherTodayFragment();
            navigationView.setCheckedItem(R.id.nav_city);
        }
        else{
            fragment = new ChooseCityFragment();
            navigationView.setCheckedItem(R.id.nav_city);
        }

        fragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit();
    }

    private void initDrawer(Toolbar toolbar) {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = findViewById(R.id.nav_view);
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
        Fragment fragment = null;
        int id = item.getItemId();


        if (id == R.id.nav_city) {
            fragment = new ChooseCityFragment();
        } else if (id == R.id.nav_day) {
            fragment = new WetherTodayFragment();

        }
        else if (id == R.id.nav_three_days) {
            fragment = new ShowSensors();
        }
        else if (id == R.id.nav_ten_days) {

        }
        else if (id == R.id.nav_month) {

        }
        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}


