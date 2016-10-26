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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.clemzux.gestionplatsdujourchattanga.R;
import com.example.clemzux.gestionplatsdujourchattanga.classes.consultdaydish.CConsultDayDish;
import com.example.clemzux.gestionplatsdujourchattanga.classes.home.CHome;
import com.example.clemzux.gestionplatsdujourchattanga.classes.utils.CJsonDecoder;
import com.example.clemzux.gestionplatsdujourchattanga.classes.utils.CProperties;
import com.example.clemzux.gestionplatsdujourchattanga.classes.utils.CRestRequest;
import com.example.clemzux.gestionplatsdujourchattanga.classes.utils.CUtilitaries;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

import chattanga.classes.CReservation;

public class CConsultReservation extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    //////// attributes ////////


    private Spinner daySpinner, monthSpinner, yearSpinner;
    private ListView reservationsListView;
    private ImageButton searchButton;

    private List<CReservation> reservations;


    //////// methods ////////


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

        // my code

        initWidgets();

        initListeners();

        populateWidgets();
    }

    private void initListeners() {

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String date = (String) daySpinner.getSelectedItem() + "-" +
                        (String) monthSpinner.getSelectedItem() + "-" +
                        String.valueOf(yearSpinner.getSelectedItem());

                populateListView(date);
            }
        });
    }

    private void populateWidgets() {

        ArrayAdapter<CharSequence> adapterDays = ArrayAdapter.createFromResource(this, R.array.days_array, android.R.layout.simple_spinner_item);
        adapterDays.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(adapterDays);

        ArrayAdapter<CharSequence> adapterMonths = ArrayAdapter.createFromResource(this, R.array.months_array, android.R.layout.simple_spinner_item);
        adapterMonths.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(adapterMonths);

        ArrayAdapter<CharSequence> adapterYears = ArrayAdapter.createFromResource(this, R.array.years_array, android.R.layout.simple_spinner_item);
        yearSpinner.setAdapter(adapterYears);

        populateListView(CUtilitaries.getInstance().getCurrentDate());
    }

    private void populateListView(String pDate) {

        try {

            getTodayReservations(pDate);

            String[] reservationsResume = new String[reservations.size()];
            int i = 0;

            if (reservations.size() != 0) {

                for (CReservation reservation : reservations) {

                    reservationsResume[i] = reservation.getName() +
                            "\n heure d'arrivée : " + reservation.getHourArrive() +
                            "\n numéro de téléphone : " + reservation.getTel() +
                            "\n nombre pers : " + reservation.getNumberPeople() +
                            "\n nombre plats du jour :" + reservation.getNumberDayDish() +
                            "\n remarque : " + reservation.getNote();
                    i++;
                }


            }
            else {

                reservationsResume = new String[1];
                reservationsResume[0] = "Aucune réservation a cette date !";
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, reservationsResume);
            reservationsListView.setAdapter(adapter);

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initWidgets() {

        daySpinner = (Spinner) findViewById(R.id.spinner_day_consult_reservations);
        monthSpinner = (Spinner) findViewById(R.id.spinner_month_consult_reservations);
        yearSpinner = (Spinner) findViewById(R.id.year_spinner_consult_reservations);

        searchButton = (ImageButton) findViewById(R.id.search_button_consult_reservations);

        reservationsListView = (ListView) findViewById(R.id.reservations_listView_consult_reservations);
    }

    private void getTodayReservations(String pTodayDate) throws ExecutionException, InterruptedException, IOException, JSONException {

        reservations = new CJsonDecoder<CReservation>().DecoderList(CRestRequest.get_reservationByDate(pTodayDate), CReservation.class);
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
            CUtilitaries.messageLong(pContext, "T'y est deja gros ;-)");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }
}
