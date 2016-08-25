package com.example.clemzux.gestionplatsdujourchattanga.classes.utils;

import android.os.AsyncTask;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

/**
 * Created by clemzux on 25/08/16.
 */
public class CRestRequest {


    private static Object sObject;


    // Fonction which is setting url and call asynctask for POST requests
    public static String post(Object pObject, String pObjectType) throws ExecutionException, InterruptedException {
        String returnValue = "0";
        sObject = pObject;
        String mUrlString = CUtils.SERVER_URL + pObjectType;
        returnValue = (new post_Put().execute(mUrlString, CUtils.POST).get());
        Log.d("rest : ",returnValue);
        return returnValue;
    }

    // Fonction which is setting url and call asynctask for PUT requests
    public static void  put(Object pObject, String pObjectType) throws ExecutionException, InterruptedException {
        sObject = pObject;

        String mUrlString = CUtils.SERVER_URL + pObjectType;

        new post_Put().execute(mUrlString, CUtils.PUT);
    }


    //////// asynctask for rest requests ////////


    private static class post_Put extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            String mUrlString = params[0];
            String mRequestType = params[1];
            String mJsonTosend = null;
            String mValueFromServer = "";

            ObjectMapper mMapper = new ObjectMapper();
            try {
                mJsonTosend = mMapper.writeValueAsString(sObject);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            HttpURLConnection mUrlConnection = initializeConnection(mUrlString);

            try {
                mUrlConnection.setRequestProperty("Content-Type", "application/json");
                mUrlConnection.setRequestMethod(CUtils.POST);
                if ((mRequestType.equals(CUtils.PUT))& !mRequestType.isEmpty())
                {
                    mUrlConnection.setRequestMethod(CUtils.PUT);
                }

                mUrlConnection.setDoInput(true);
                mUrlConnection.setDoOutput(true);
                OutputStream mOutput = new BufferedOutputStream(mUrlConnection.getOutputStream());
                mOutput.write(mJsonTosend.getBytes());
                mOutput.flush();
                mValueFromServer = mUrlConnection.getResponseMessage();

            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return mValueFromServer;
        }
    }


    // Asynctask for GET, GETALL and DEL methods
    private static class get_Del extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            String mUrlString = params[0];
            String mRequestType = params[1];
            String mResult = null;

            HttpURLConnection mUrlConnection = initializeConnection(mUrlString);

            if ((mRequestType.equals(CUtils.GET) | mRequestType.equals(CUtils.GET_ALL) | mRequestType.equals(CUtils.GET_BY) & !mRequestType.isEmpty())) {
                try {
                    mUrlConnection.connect();
                    InputStream mInputStream = mUrlConnection.getInputStream();
                    mResult = CInputStreamOperations.InputStreamToString(mInputStream);
                } catch (Exception e){
                }
            }

            if (mRequestType.equals(CUtils.DELETE)) {
                try {
                    mUrlConnection.setRequestMethod(CUtils.DELETE);
                    mUrlConnection.setRequestProperty("Content-type", "application/json");

                } catch (ProtocolException e) {
                    e.printStackTrace();
                }
                try {
                    int res = mUrlConnection.getResponseCode();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mResult = " Deleted";
            }
            return mResult;
        }
    }


    // Initialize the request's connection
    private static HttpURLConnection initializeConnection(String pUrlString){
        URL url = null;
        try {
            url = new URL(pUrlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        System.out.println(pUrlString);
        HttpURLConnection mUrlConnection = null;
        try {
            mUrlConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mUrlConnection;
    }
}