package com.example.clemzux.gestionplatsdujourchattanga.classes.consultdaydish;

import com.example.clemzux.gestionplatsdujourchattanga.classes.utils.CJsonDecoder;
import com.example.clemzux.gestionplatsdujourchattanga.classes.utils.CRestRequest;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import chattanga.classes.CDate;

/**
 * Created by clemzux on 26/10/16.
 */
public class CConsultDayDishModel {
    private static CConsultDayDishModel ourInstance = new CConsultDayDishModel();

    public static CConsultDayDishModel getInstance() {
        return ourInstance;
    }

    private CConsultDayDishModel() {}


    //////// methods ////////


    public List<CDate> getDayDishs() throws ExecutionException, InterruptedException, IOException, JSONException {

        String mRequest = CRestRequest.get_All("dates");
        CJsonDecoder<CDate> mDatesCJsonDecoder = new CJsonDecoder<CDate>();

        return mDatesCJsonDecoder.DecoderList(mRequest, CDate.class);
    }
}
