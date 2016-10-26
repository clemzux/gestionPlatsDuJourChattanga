package com.example.clemzux.gestionplatsdujourchattanga.classes.home;

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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clemzux.gestionplatsdujourchattanga.R;
import com.example.clemzux.gestionplatsdujourchattanga.classes.camera.CCamera;
import com.example.clemzux.gestionplatsdujourchattanga.classes.consultdaydish.CConsultDayDish;
import com.example.clemzux.gestionplatsdujourchattanga.classes.consultreservations.CConsultReservation;
import com.example.clemzux.gestionplatsdujourchattanga.classes.utils.CJsonDecoder;
import com.example.clemzux.gestionplatsdujourchattanga.classes.utils.CProperties;
import com.example.clemzux.gestionplatsdujourchattanga.classes.utils.CRestRequest;
import com.example.clemzux.gestionplatsdujourchattanga.classes.utils.CUtilitaries;

import java.io.IOException;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

import chattanga.classes.CDate;

public class CHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    //////// vars ////////


    private Spinner daysSpinner, monthsSpinner;
    private TextView yearTextView;
    private EditText dayDishEditText, imageIdentifierEditText;
    private Button validateButton;

    private int currentYear;
    private String day, month, dateComplete, dayDish;

    private ActionBarDrawerToggle toggle;


    //////// methods ////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // initialisation des widgets

        initWidgets();

        // initialiser les spinners

        initSpinners();

        // initialisation de l'année en cours

        initCurrentYear();

        // initialisation listener

        initListeners();
    }


    private void initCurrentYear() {

        currentYear = Calendar.getInstance().get(Calendar.YEAR);
        yearTextView.setText(String.valueOf(currentYear));
    }


    private void initListeners() {

        // listener du bouton valider

        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validateButton();
            }
        });
    }


    private void validateButton() {

        day = daysSpinner.getSelectedItem().toString();
        month = monthsSpinner.getSelectedItem().toString();
        dateComplete = day + "-" + month + "-" + currentYear;

        dayDish = dayDishEditText.getText().toString();

        if (day.equals("Jour"))
            alert(1);
        else {

            if (month.equals("Mois"))
                alert(2);
            else {

                if (dayDish.length() < 10)
                    alert(3);
                else {

                    try {

                        sendDayDishToServer();

                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    private void sendDayDishToServer() throws ExecutionException, InterruptedException {

        // objet a envoyer

        CDate date = new CDate();
        date.setDate(dateComplete);
        date.setDayDish(dayDish);
        date.setImageIdentifier(String.valueOf(imageIdentifierEditText.getText()));

        if (isDayDishDateUnic(date)) {

            CRestRequest.put(date, "dates");
            resetWidgets();
            CUtilitaries.messageLong(this, "Le plat du jour à bien été enregistré !");
        }
        else {

            CUtilitaries.messageLong(this, "Un plat du jour existe déja à cette date !");
        }
    }

    private Boolean isDayDishDateUnic(CDate pDate) {

        try {

            CDate date = new CJsonDecoder<CDate>().Decoder(CRestRequest.get_dateByDate(pDate.getDate()), CDate.class);

            if (date.getDate() != null)
                if (pDate.getDate().equals(date.getDate()))
                    return false;

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }


    private void resetWidgets() {

        dayDishEditText.setText("");
        imageIdentifierEditText.setText("");
    }


    private void alert(int pAlertType) {

        String alertText;

        if (pAlertType == 1)
            alertText = "Le jour n'a pas été choisit !";
        else {

            if (pAlertType == 2)
                alertText = "Le mois n'a pas été choisit !";
            else {

                if (pAlertType == 3)
                    alertText = "Le plat du jour n'a pas été saisit !";
                else {
                    alertText = "ok";
                }
            }
        }

        Toast toast = Toast.makeText(getApplicationContext(), alertText, Toast.LENGTH_SHORT);
        toast.show();
    }


    private void initSpinners() {

        // spinner des jours

        ArrayAdapter<CharSequence> daysAdapter = ArrayAdapter.createFromResource(this,
                R.array.days_array, android.R.layout.simple_spinner_item);
        daysAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daysSpinner.setAdapter(daysAdapter);

        // spinner des mois

        ArrayAdapter<CharSequence> monthsAdapter = ArrayAdapter.createFromResource(this,
                R.array.months_array, android.R.layout.simple_spinner_item);
        monthsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthsSpinner.setAdapter(monthsAdapter);
    }


    private void initWidgets() {

        daysSpinner = (Spinner) findViewById(R.id.days_spinner);
        monthsSpinner = (Spinner) findViewById(R.id.month_spinner);

        yearTextView = (TextView) findViewById(R.id.year_textView);

        dayDishEditText = (EditText) findViewById(R.id.dayDish_editText);
        imageIdentifierEditText = (EditText) findViewById(R.id.image_identifier_editText);

        validateButton = (Button) findViewById(R.id.validate_button);
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

        activityOpener(getApplicationContext(), pItem, CProperties.HOME_ACTIVITY);

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
        else if (id == R.id.nav_camera && !pComingFrom.equals(CProperties.CAMERA_ACTIVITY)) {

            Intent cameraIntent = new Intent(pContext, CCamera.class);
            startActivity(cameraIntent);
        }
        else {
            CUtilitaries.messageLong(pContext, "T'y est deja gros ;-)");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }
}
