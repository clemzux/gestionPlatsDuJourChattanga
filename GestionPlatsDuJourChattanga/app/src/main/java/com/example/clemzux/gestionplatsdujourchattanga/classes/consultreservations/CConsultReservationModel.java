package com.example.clemzux.gestionplatsdujourchattanga.classes.consultreservations;

import com.example.clemzux.gestionplatsdujourchattanga.classes.utils.CJsonDecoder;
import com.example.clemzux.gestionplatsdujourchattanga.classes.utils.CRestRequest;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import chattanga.classes.CReservation;

/**
 * Created by clemzux on 26/10/16.
 */
public class CConsultReservationModel {
    private static CConsultReservationModel ourInstance = new CConsultReservationModel();

    public static CConsultReservationModel getInstance() {
        return ourInstance;
    }

    private CConsultReservationModel() {}


    //////// methods ////////


    public List<CReservation> getTodayReservations(String pTodayDate) {

        try {
            return new CJsonDecoder<CReservation>().DecoderList(CRestRequest.get_reservationByDate(pTodayDate), CReservation.class);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new ArrayList<CReservation>();
    }
}
