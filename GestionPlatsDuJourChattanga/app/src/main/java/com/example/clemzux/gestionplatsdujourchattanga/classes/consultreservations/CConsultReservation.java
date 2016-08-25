package com.example.clemzux.gestionplatsdujourchattanga.classes.consultreservations;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.clemzux.gestionplatsdujourchattanga.R;
import com.example.clemzux.gestionplatsdujourchattanga.classes.consultdaydish.CConsultDayDish;
import com.example.clemzux.gestionplatsdujourchattanga.classes.home.CHome;
import com.example.clemzux.gestionplatsdujourchattanga.classes.utils.CProperties;
import com.example.clemzux.gestionplatsdujourchattanga.classes.utils.CUtilitaries;

public class CConsultReservation extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_consult_reservations);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

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
        getMenuInflater().inflate(R.menu.home, menu);
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
    public boolean onNavigationItemSelected(MenuItem pItem) {

        activityOpener(getApplicationContext(), pItem, CProperties.RESERVATION_ACTIVITY);

        return true;
    }

    public void activityOpener(Context pContext, MenuItem pItem, String pComingFrom) {

        int id = pItem.getItemId();

        if (id == R.id.nav_new_dayDish && !pComingFrom.equals(CProperties.HOME_ACTIVITY)) {

            Intent homeIntent = new Intent(pContext, CHome.class);
            startActivity(homeIntent);
        }
        else if (id == R.id.nav_consult_dayDish && !pComingFrom.equals(CProperties.DAYDISH_ACTIVITY)) {

            Intent consultDayDishIntent = new Intent(pContext, CConsultDayDish.class);
            startActivity(consultDayDishIntent);
        }
        else if (id == R.id.nav_consult_reservations && !pComingFrom.equals(CProperties.RESERVATION_ACTIVITY)) {

            Intent consultReservationsIntent = new Intent(pContext, CConsultReservation.class);
            startActivity(consultReservationsIntent);
        }
        else {
            CUtilitaries.test(pContext, "T'y est deja gros ;-)");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }
}
