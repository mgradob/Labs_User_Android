package com.itesm.labs.labsuser.activities.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.activities.adapters.CatAdapter;
import com.itesm.labs.labsuser.activities.async_tasks.HiloCat;
import com.itesm.labs.labsuser.activities.rest.models.Category;

import java.util.ArrayList;

public class CategoriesGridFragment extends Fragment {

    private CatGridFragInteractionListener catGridFragInteractionListener;
    private GridView mCategoriesGrid;
    private SwipeRefreshLayout refreshLayout;

    private String ENDPOINT;
    private ArrayList<Category> listaCat;

    public String getENDPOINT() {
        return ENDPOINT;
    }

    public void setENDPOINT(String ENDPOINT) {
        this.ENDPOINT = ENDPOINT;
    }

    public CategoriesGridFragment() {
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
        return inflater.inflate(R.layout.fragment_categories_grid, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fragment_categories_grid_swipe_refresh);
        refreshLayout.setColorSchemeResources(R.color.material_blue, R.color.material_yellow, R.color.material_red);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshList();
            }
        });

        mCategoriesGrid = (GridView) view.findViewById(R.id.fragment_categories_grid_gridview);
        mCategoriesGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                catGridFragInteractionListener.displayCategory(listaCat.get(position).getId());
            }
        });

        HiloCat hilo = new HiloCat() {
            @Override
            protected void onPostExecute(ArrayList<Category> categories) {
                super.onPostExecute(categories);

                if (categories != null) {
                    listaCat = categories;

                    mCategoriesGrid.setAdapter(new CatAdapter(listaCat, getActivity().getApplicationContext()));
                }
            }
        };
        hilo.execute(ENDPOINT);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            catGridFragInteractionListener = (CatGridFragInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement CatGridFragInteractionListener");
        }
    }

    private void refreshList() {
        refreshLayout.setRefreshing(true);

        HiloCat hilo = new HiloCat() {
            @Override
            protected void onPostExecute(ArrayList<Category> categories) {
                super.onPostExecute(categories);

                if (categories != null) {
                    listaCat = categories;

                    mCategoriesGrid.setAdapter(new CatAdapter(listaCat, getActivity().getApplicationContext()));
                    refreshLayout.setRefreshing(false);
                }
            }
        };
        hilo.execute(ENDPOINT);
    }

    /**
     * Interface para comunicarse con actividad superior y otros fragmentos.
     */
    public interface CatGridFragInteractionListener {
        void displayCategory(int categoryId);
    }
}
