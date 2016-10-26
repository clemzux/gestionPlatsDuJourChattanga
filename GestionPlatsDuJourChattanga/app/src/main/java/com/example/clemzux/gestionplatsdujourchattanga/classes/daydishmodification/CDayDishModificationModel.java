package com.example.clemzux.gestionplatsdujourchattanga.classes.daydishmodification;

import android.content.Intent;

import com.example.clemzux.gestionplatsdujourchattanga.classes.consultdaydish.CConsultDayDish;
import com.example.clemzux.gestionplatsdujourchattanga.classes.utils.CRestRequest;
import com.example.clemzux.gestionplatsdujourchattanga.classes.utils.CUtilitaries;

import java.util.concurrent.ExecutionException;

import chattanga.classes.CDate;

/**
 * Created by clemzux on 26/10/16.
 */
public class CDayDishModificationModel {
    private static CDayDishModificationModel ourInstance = new CDayDishModificationModel();

    public static CDayDishModificationModel getInstance() {
        return ourInstance;
    }

    private CDayDishModificationModel() {}


    //////// methods ////////


    public void saveDayDishModification(CDate pDate) {


        try {
            CRestRequest.post(pDate, "dates");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
