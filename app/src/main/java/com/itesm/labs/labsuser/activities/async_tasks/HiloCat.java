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
public class HiloCat extends AsyncTask<String, Void, ArrayList<Category>> {

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

        for (Category category : categoryArrayList) {
            if (category.getId() == 0) category.setImageResource(R.drawable.resistencias);
            else if (category.getId() == 1) category.setImageResource(R.drawable.capacitores);
            else if (category.getId() == 2) category.setImageResource(R.drawable.equipo);
            else if (category.getId() == 3) category.setImageResource(R.drawable.kits);
            else if (category.getId() == 4) category.setImageResource(R.drawable.cables);
            else if (category.getId() == 5) category.setImageResource(R.drawable.integrados);
            else if (category.getId() == 6) category.setImageResource(R.drawable.diodos);
            else if (category.getId() == 7) category.setImageResource(R.drawable.herramientas);
            else if (category.getId() == 8) category.setImageResource(R.drawable.switches);
            else if (category.getId() == 9) category.setImageResource(R.drawable.adaptadores);
            else if (category.getId() == 10) category.setImageResource(R.drawable.displays);
            else if (category.getId() == 11) category.setImageResource(R.drawable.inductores);
            else if (category.getId() == 12) category.setImageResource(R.drawable.sensores);
            else if (category.getId() == 13) category.setImageResource(R.drawable.motores);
            else if (category.getId() == 14) category.setImageResource(R.drawable.potenciometro);
            else if (category.getId() == 15) category.setImageResource(R.drawable.transformadores);
            else if (category.getId() == 16) category.setImageResource(R.drawable.transistores);
            else category.setImageResource(R.drawable.ic_career);
        }

        return categoryArrayList;
    }
}
