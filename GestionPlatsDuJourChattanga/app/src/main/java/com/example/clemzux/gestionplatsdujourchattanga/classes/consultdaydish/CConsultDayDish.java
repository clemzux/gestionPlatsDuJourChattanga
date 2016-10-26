package com.example.clemzux.gestionplatsdujourchattanga.classes.consultdaydish;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.clemzux.gestionplatsdujourchattanga.R;
import com.example.clemzux.gestionplatsdujourchattanga.classes.consultreservations.CConsultReservation;
import com.example.clemzux.gestionplatsdujourchattanga.classes.home.CHome;
import com.example.clemzux.gestionplatsdujourchattanga.classes.utils.CJsonDecoder;
import com.example.clemzux.gestionplatsdujourchattanga.classes.utils.CProperties;
import com.example.clemzux.gestionplatsdujourchattanga.classes.utils.CRestRequest;
import com.example.clemzux.gestionplatsdujourchattanga.classes.utils.CUtilitaries;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutionException;

import chattanga.classes.CDate;

public class CConsultDayDish extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    //////// attributes ////////


    private ListView dayDishListView;
    private List<CDate> dayDishList = null;
    private String[] dayDishNames = null;


    //////// methods ////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_consult_day_dish);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // on recupere les plats du jours déja créés

        try {
            getDayDishs();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // initialisartion des widgets

        initWidgets();

        // initialisation des listeners

        initListeners();
    }


    private void initWidgets() {

        dayDishListView = (ListView) findViewById(R.id.daydish_listView);
        initListView();
    }


    private void initListView() {

        int i = 0;

        dayDishNames = new String[dayDishList.size()];

        for (CDate dayDish : dayDishList) {
            dayDishNames[i] = dayDish.getDate() + " " + dayDish.getDayDish();
            i++;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dayDishNames);
        dayDishListView.setAdapter(adapter);
    }


    private void initListeners() {

        dayDishListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                openDayDishModificationActivity(i);
            }
        });
    }


    private void openDayDishModificationActivity(int pDayDishNumber) {

        Intent modificationDayDishIntent = new Intent(getBaseContext(), CDayDishModification.class);
        modificationDayDishIntent.putExtra("id", String.valueOf(dayDishList.get(pDayDishNumber).getId()));
        modificationDayDishIntent.putExtra("date", dayDishList.get(pDayDishNumber).getDate());
        modificationDayDishIntent.putExtra("dayDish", dayDishList.get(pDayDishNumber).getDayDish());
        modificationDayDishIntent.putExtra("imageIdentifier", dayDishList.get(pDayDishNumber).getImageIdentifier());

        startActivity(modificationDayDishIntent);
    }


    private void getDayDishs() throws ExecutionException, InterruptedException, IOException, JSONException {

        String mRequest = CRestRequest.get_All("dates");
        CJsonDecoder<CDate> mDatesCJsonDecoder = new CJsonDecoder<CDate>();

        dayDishList = mDatesCJsonDecoder.DecoderList(mRequest, CDate.class);
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

        activityOpener(getApplicationContext(), pItem, CProperties.DAYDISH_ACTIVITY);

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
            CUtilitaries.messageLong(pContext, "T'y est deja gros ;-)");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }
}