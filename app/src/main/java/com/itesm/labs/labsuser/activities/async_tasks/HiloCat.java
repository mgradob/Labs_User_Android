package com.itesm.labs.labsuser.activities.async_tasks;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.activities.rest.models.Category;
import com.itesm.labs.labsuser.activities.rest.service.CategoryService;

import java.util.ArrayList;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by mgradob on 2/15/15.
 */
public class HiloCat extends AsyncTask<String, Void, ArrayList<Category>> {

    @Override
    protected ArrayList<Category> doInBackground(String... params) {
        try {
            ArrayList<Category> categoryArrayList;

            Gson gson = new GsonBuilder().create();

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(params[0])
                    .setConverter(new GsonConverter(gson))
                    .build();

            CategoryService catInterface = restAdapter.create(CategoryService.class);

            categoryArrayList = catInterface.getCategories();

            for (Category category : categoryArrayList) {
                switch (category.getId()) {
                    case 1:
                        category.setImageResource(R.drawable.resistencias);
                        break;
                    case 2:
                        category.setImageResource(R.drawable.capacitores);
                        break;
                    case 3:
                        category.setImageResource(R.drawable.equipo);
                        break;
                    case 4:
                        category.setImageResource(R.drawable.kits);
                        break;
                    case 5:
                        category.setImageResource(R.drawable.cables);
                        break;
                    case 6:
                        category.setImageResource(R.drawable.integrados);
                        break;
                    case 7:
                        category.setImageResource(R.drawable.diodos);
                        break;
                    case 8:
                        category.setImageResource(R.drawable.herramientas);
                        break;
                    case 9:
                        category.setImageResource(R.drawable.switches);
                        break;
                    case 10:
                        category.setImageResource(R.drawable.adaptadores);
                        break;
                    case 11:
                        category.setImageResource(R.drawable.displays);
                        break;
                    case 12:
                        category.setImageResource(R.drawable.inductores);
                        break;
                    case 13:
                        category.setImageResource(R.drawable.sensores);
                        break;
                    case 14:
                        category.setImageResource(R.drawable.motores);
                        break;
                    case 15:
                        category.setImageResource(R.drawable.potenciometro);
                        break;
                    case 16:
                        category.setImageResource(R.drawable.transformadores);
                        break;
                    case 17:
                        category.setImageResource(R.drawable.transistores);
                        break;
                    default:
                        category.setImageResource(R.drawable.ic_career);
                        break;
                }
            }

            return categoryArrayList;
        }catch (Exception ex){
            return null;
        }
    }
}
