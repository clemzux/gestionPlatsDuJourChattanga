package com.example.clemzux.gestionplatsdujourchattanga.classes.home;

import com.example.clemzux.gestionplatsdujourchattanga.classes.utils.CJsonDecoder;
import com.example.clemzux.gestionplatsdujourchattanga.classes.utils.CRestRequest;
import com.example.clemzux.gestionplatsdujourchattanga.classes.utils.CUtilitaries;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import chattanga.classes.CDate;

/**
 * Created by clemzux on 26/10/16.
 */
public class CHomeModel {
    private static CHomeModel ourInstance = new CHomeModel();

    public static CHomeModel getInstance() {
        return ourInstance;
    }

    private CHomeModel() {}


    //////// methods ////////


    public void sendDayDishToServer(CDate pDate, CHome pHome) throws ExecutionException, InterruptedException {



        if (isDayDishDateUnic(pDate))
            CRestRequest.put(pDate, "dates");

        else {

            CUtilitaries.messageLong(pHome, "Un plat du jour existe déja à cette date !");
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
}
