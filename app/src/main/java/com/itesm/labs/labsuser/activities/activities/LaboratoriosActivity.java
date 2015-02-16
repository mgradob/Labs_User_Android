package com.itesm.labs.labsuser.activities.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.activities.adapters.LabsAdapter;
import com.itesm.labs.labsuser.activities.async_tasks.HiloLabs;
import com.itesm.labs.labsuser.activities.rest.models.Laboratory;

import java.util.ArrayList;

public class LaboratoriosActivity extends ActionBarActivity {

    private GridView gridLabs;
    private String ENDPOINT = "http://labs.chi.itesm.mx:8080";
    private ArrayList <Laboratory> ListaLabs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laboratorios);

        gridLabs = (GridView) findViewById(R.id.labs_grid);

        gridLabs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), CategoriesActivity.class);

                intent.putExtra("ENDPOINT", ListaLabs.get(position).getUrl());
                startActivity(intent);
                overridePendingTransition(R.anim.abc_slide_in_top,R.anim.abc_slide_in_top);

            }
        });

        HiloLabs hilo = new HiloLabs(){
            @Override
            protected void onPostExecute(ArrayList<Laboratory> laboratories) {
                super.onPostExecute(laboratories);

                ListaLabs = laboratories;

                gridLabs.setAdapter(new LabsAdapter(ListaLabs, getApplicationContext()));
            }
        };
        hilo.execute(ENDPOINT);
    }
}
