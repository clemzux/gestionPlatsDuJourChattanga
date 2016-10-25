package com.example.clemzux.gestionplatsdujourchattanga.classes.utils;

import android.support.v7.app.ActionBarDrawerToggle;

import chattanga.classes.CDate;

/**
 * Created by clemzux on 24/08/16.
 */
public class CProperties {

    // ip chattanga
//    public static final String SERVER_URL = "http://176.157.85.69:9999/";
    // ip maison capi
//    public static final String SERVER_URL = "http://192.168.0.195:9999/";
    // ip bureau capi
    public static final String SERVER_URL = "http://192.168.1.91:9999/";
    // ip maison mémé
//    public static final String SERVER_URL = "http://192.168.1.17:9999/";
    // ip fac salle u026
//    public static final String SERVER_URL = "http://10.21.157.209:9999/";

    // request types
    public final static String GET      = "GET";
    public final static String POST     = "POST";
    public final static String PUT      = "PUT";
    public final static String DELETE   = "DEL";
    public final static String GET_ALL  = "GET_ALL";
    public final static String GET_BY   = "GET_BY";

    // paths for requests
    public final static String DATES = "dates";
    public final static String RESERVATIONS = "reservations";

    // Activity names
    public final static String HOME_ACTIVITY = "home";
    public final static String RESERVATION_ACTIVITY = "reservations";
    public final static String DAYDISH_ACTIVITY = "daydish";
    public final static String CAMERA_ACTIVITY = "camera";

    // other
    public final static String SELECTED_DAYDISH = "selectedDayDish";
    public final static String DAYDISH_MODIFICATION = "dayDishModification";
}
