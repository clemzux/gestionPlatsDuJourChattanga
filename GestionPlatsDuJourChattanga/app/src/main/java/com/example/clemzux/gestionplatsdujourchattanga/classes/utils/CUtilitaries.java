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

    public static void test(Context pContext, String pVar) {

        Toast t = Toast.makeText(pContext, pVar, Toast.LENGTH_SHORT);
        t.show();
    }


}
