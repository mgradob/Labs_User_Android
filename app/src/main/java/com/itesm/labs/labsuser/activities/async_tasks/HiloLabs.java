package com.itesm.labs.labsuser.activities.async_tasks;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.activities.rest.interfaces.LabsInterface;
import com.itesm.labs.labsuser.activities.rest.models.Laboratory;

import java.util.ArrayList;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by mgradob on 2/15/15.
 */
public class HiloLabs extends AsyncTask<String, Void, ArrayList<Laboratory>> {
    @Override
    protected ArrayList<Laboratory> doInBackground(String... params) {
        ArrayList<Laboratory> laboratoryArrayList;

        Gson gson = new GsonBuilder().create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(params[0])
                .setConverter(new GsonConverter(gson))
                .build();

        LabsInterface labsInterface = restAdapter.create(LabsInterface.class);

        laboratoryArrayList = labsInterface.getLabs();

        for(Laboratory laboratory : laboratoryArrayList){
            laboratory.setImageResource(R.drawable.ic_logo);
        }

        return laboratoryArrayList;
    }
}
