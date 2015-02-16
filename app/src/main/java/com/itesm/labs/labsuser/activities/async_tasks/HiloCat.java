package com.itesm.labs.labsuser.activities.async_tasks;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.activities.rest.interfaces.CatInterface;
import com.itesm.labs.labsuser.activities.rest.models.Category;

import java.util.ArrayList;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by mgradob on 2/15/15.
 */
public class HiloCat extends AsyncTask<String,Void,ArrayList<Category>> {

    @Override
    protected ArrayList<Category> doInBackground(String... params) {
        ArrayList<Category> categoryArrayList;

        Gson gson = new GsonBuilder().create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(params[0])
                .setConverter(new GsonConverter(gson))
                .build();

        CatInterface catInterface = restAdapter.create(CatInterface.class);

        categoryArrayList = catInterface.getCat();

        for(Category category : categoryArrayList){
            category.setImageResource(R.drawable.ic_career);
        }

        return categoryArrayList;
    }
}
