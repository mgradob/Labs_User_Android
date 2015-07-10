package com.itesm.labs.labsuser.activities.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.activities.adapters.LabsAdapter;
import com.itesm.labs.labsuser.activities.rest.models.Laboratory;
import com.itesm.labs.labsuser.activities.rest.service.LaboratoryService;

import java.util.ArrayList;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public class LaboratoriesActivity extends ActionBarActivity {

    private GridView gridLabs;
    private SwipeRefreshLayout refreshLayout;

    private String userId;
    private ArrayList<String> allowedLabs;
    private ArrayList<Laboratory> ListaLabs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laboratorios);

        gridLabs = (GridView) findViewById(R.id.labs_grid);

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.labs_grid_swipe_refresh);
        refreshLayout.setColorSchemeResources(R.color.material_blue, R.color.material_yellow, R.color.material_red);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshList();
            }
        });

        Intent callingIntent = getIntent();
        userId = callingIntent.getStringExtra("USERID");
        allowedLabs = callingIntent.getStringArrayListExtra("ALLOWEDLABS");

        gridLabs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);

                intent.putExtra("ENDPOINT", ListaLabs.get(position).getLink());
                intent.putExtra("LABNAME", ListaLabs.get(position).getName());
                intent.putExtra("COLOR", ListaLabs.get(position).getColorResource());
                intent.putExtra("USERID", userId);
                startActivity(intent);
                overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_in_top);
            }
        });

        refreshList();
    }

    private void refreshList() {
        new AsyncTask<ArrayList<String>, Void, ArrayList<Laboratory>>() {
            @Override
            protected ArrayList<Laboratory> doInBackground(ArrayList<String>... params) {
                try {
                    ArrayList<Laboratory> labsList = new ArrayList<>();

                    for (String labName : params[0]) {
                        Gson gson = new GsonBuilder().create();

                        RestAdapter restAdapter = new RestAdapter.Builder()
                                .setEndpoint(labName)
                                .setConverter(new GsonConverter(gson))
                                .build();

                        LaboratoryService labsInterface = restAdapter.create(LaboratoryService.class);

                        labsList.add(labsInterface.getLaboratoriesFromUrl());
                    }

                    for (Laboratory laboratory : labsList) {
                        laboratory.setImageResource(R.drawable.ic_logo);
                    }

                    return labsList;
                } catch (Exception ex) {
                    Log.e("LABS", "Error finding labs");
                    return null;
                }
            }

            @Override
            protected void onPostExecute(ArrayList<Laboratory> laboratories) {
                super.onPostExecute(laboratories);

                if (laboratories != null) {
                    ListaLabs = laboratories;
                    gridLabs.setAdapter(new LabsAdapter(ListaLabs, getApplicationContext()));
                }
            }
        }.execute(allowedLabs);
    }
}
