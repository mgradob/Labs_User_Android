package com.itesm.labs.labsuser.activities.fragments;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.activities.adapters.RecordAdapter;
import com.itesm.labs.labsuser.activities.rest.models.Category;
import com.itesm.labs.labsuser.activities.rest.models.Component;
import com.itesm.labs.labsuser.activities.rest.models.History;
import com.itesm.labs.labsuser.activities.rest.queries.CategoryQuery;
import com.itesm.labs.labsuser.activities.rest.queries.ComponentQuery;
import com.itesm.labs.labsuser.activities.rest.queries.RecordQuery;

import java.security.AccessControlContext;
import java.util.ArrayList;

public class RecordFragment extends Fragment {

    private ListView recordListView;
    private SwipeRefreshLayout recordRefreshLayout;

    private String ENDPOINT;
    private String USER_ID;
    private ArrayList<History> mHistoryArrayList;
    private Activity parentActivity;

    public String getENDPOINT() {
        return ENDPOINT;
    }

    public void setENDPOINT(String ENDPOINT) {
        this.ENDPOINT = ENDPOINT;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public Activity getParentActivity() {
        return parentActivity;
    }

    public void setParentActivity(Activity parentActivity) {
        this.parentActivity = parentActivity;
    }

    public RecordFragment() {
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
        return inflater.inflate(R.layout.fragment_record, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recordRefreshLayout = (SwipeRefreshLayout)
                view.findViewById(R.id.record_list_view_swipe_refresh);
        recordRefreshLayout.setColorSchemeResources(R.color.material_blue, R.color.material_yellow,
                R.color.material_red);
        recordRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshList();
            }
        });

        recordListView = (ListView) view.findViewById(R.id.record_list_view);
    }

    public void refreshList(){
        new AsyncTask<Void, Void, ArrayList<History>>(){
            @Override
            protected ArrayList<History> doInBackground(Void... params) {
                ArrayList<History> tempHistories = new RecordQuery(ENDPOINT).getRecordOf(USER_ID);

                for(History item : tempHistories){
                    Component tempComponent = new ComponentQuery(ENDPOINT)
                            .getComponent(item.getComponentId());

                    Category tempCategory = new CategoryQuery(ENDPOINT)
                            .getCategoryOf(tempComponent);

                    item.setCategoryName(tempCategory.getName());

                    String tempString = "";
                    if(tempComponent.getName() != null) tempString += tempComponent.getName();
                    else tempString += "";
                    tempString += " ";
                    if(tempComponent.getNote() != null) tempString += tempComponent.getNote();
                    else tempString += "";
                    item.setComponentNameNote(tempString);
                }

                return tempHistories;
            }

            @Override
            protected void onPostExecute(ArrayList<History> histories) {
                super.onPostExecute(histories);

                recordRefreshLayout.setRefreshing(false);

                mHistoryArrayList = histories;
                recordListView.setAdapter(new RecordAdapter(getActivity().getApplication()
                        .getApplicationContext(), mHistoryArrayList));
            }
        }.execute();
    }
}
