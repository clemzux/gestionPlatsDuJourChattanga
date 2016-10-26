package com.example.clemzux.gestionplatsdujourchattanga.classes.utils;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.clemzux.gestionplatsdujourchattanga.R;
import com.example.clemzux.gestionplatsdujourchattanga.classes.consultdaydish.CConsultDayDish;
import com.example.clemzux.gestionplatsdujourchattanga.classes.consultreservations.CConsultReservation;
import com.example.clemzux.gestionplatsdujourchattanga.classes.home.CHome;

import java.util.Calendar;

/**
 * Created by clemzux on 25/08/16.
 */
public class CUtilitaries extends AppCompatActivity{
    private static CUtilitaries ourInstance = new CUtilitaries();

    public static CUtilitaries getInstance() {
        return ourInstance;
    }

    private CUtilitaries() {
    }

    public static void messageLong(Context pContext, String pVar) {

        Toast t = Toast.makeText(pContext, pVar, Toast.LENGTH_LONG);
        t.show();
    }

    public static void messageShort(Context pContext, String pVar) {

        Toast t = Toast.makeText(pContext, pVar, Toast.LENGTH_LONG);
        t.show();
    }

    public String getCurrentDate() {

        String day, month, year;
        int hour;
        Calendar calendar = Calendar.getInstance();

        hour = calendar.get(Calendar.HOUR_OF_DAY);

        year = String.valueOf(calendar.get(Calendar.YEAR));
        month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));

        if (hour >= 15) {

            day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH) + 1);

            if (Integer.valueOf(day) > calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {

                day = "01";
                month = String.valueOf(calendar.get(Calendar.MONTH) + 2);

                if (month.equals("13")) {

                    month = "01";
                    year = String.valueOf(calendar.get(Calendar.YEAR) + 1);
                }
            }
        }

        if (month.length() == 1)
            month = "0" + month;

        if (day.length() == 1)
            day = "0" + day;

        return day + "-" + month + "-" + year;
    }
}
