package com.itesm.labs.labsuser.activities.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.activities.adapters.CatAdapter;
import com.itesm.labs.labsuser.activities.async_tasks.HiloCat;
import com.itesm.labs.labsuser.activities.rest.models.Category;

import java.util.ArrayList;

public class CategoriesActivity extends ActionBarActivity {

    private GridView gridCat;
    private String ENDPOINT;
    private ArrayList <Category> ListaCat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        gridCat = (GridView) findViewById(R.id.cat_grid);

        ENDPOINT = getIntent().getStringExtra("ENDPOINT");

        HiloCat hilo = new HiloCat(){
            @Override
            protected void onPostExecute(ArrayList<Category> categories) {
                super.onPostExecute(categories);

                ListaCat = categories;

                gridCat.setAdapter(new CatAdapter(ListaCat,getApplicationContext()));
            }
        };
        hilo.execute(ENDPOINT);

    }



}
