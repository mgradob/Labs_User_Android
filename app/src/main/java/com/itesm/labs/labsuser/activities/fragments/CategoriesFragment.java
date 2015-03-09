package com.itesm.labs.labsuser.activities.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.activities.activities.ComponentsActivity;
import com.itesm.labs.labsuser.activities.adapters.CatAdapter;
import com.itesm.labs.labsuser.activities.async_tasks.HiloCat;
import com.itesm.labs.labsuser.activities.rest.models.Category;

import java.util.ArrayList;

public class CategoriesFragment extends Fragment {

    private GridView mCategoriesGrid;
    private String ENDPOINT;
    private ArrayList<Category> listaCat;

    public String getENDPOINT() {
        return ENDPOINT;
    }

    public void setENDPOINT(String ENDPOINT) {
        this.ENDPOINT = ENDPOINT;
    }

    public CategoriesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_categories, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCategoriesGrid = (GridView) view.findViewById(R.id.fragment_categories_grid);
        mCategoriesGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity().getApplicationContext(), ComponentsActivity.class);
                intent.putExtra("CATEGORYID", listaCat.get(position).getId());
                intent.putExtra("CATEGORYTITLE", listaCat.get(position).getName());
                intent.putExtra("CATEGORYICON", listaCat.get(position).getImageResource());
                intent.putExtra("ENDPOINT", ENDPOINT);
                startActivity(intent);
            }
        });

        HiloCat hilo = new HiloCat() {
            @Override
            protected void onPostExecute(ArrayList<Category> categories) {
                super.onPostExecute(categories);

                listaCat = categories;

                mCategoriesGrid.setAdapter(new CatAdapter(listaCat, getActivity().getApplicationContext()));
            }
        };
        hilo.execute(ENDPOINT);
    }
}
