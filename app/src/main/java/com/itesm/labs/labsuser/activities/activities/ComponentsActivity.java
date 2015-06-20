package com.itesm.labs.labsuser.activities.activities;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.activities.adapters.CompAdapter;
import com.itesm.labs.labsuser.activities.rest.models.Component;
import com.itesm.labs.labsuser.activities.rest.queries.ComponentQuery;
import com.itesm.labs.labsuser.activities.sqlite.LabsUserSqliteOpenHelper;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;

public class ComponentsActivity extends ActionBarActivity {

    private String ENDPONT, TITLE;
    private Integer ICON, ID;
    private View mBackground;
    private ImageView mCategoryIcon;
    private TextView mCategoryName;
    private ListView mCategoryList;
    private FloatingActionButton mFab;
    private ArrayList<Component> componentsList;

    private LabsUserSqliteOpenHelper labsUserSqlite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_components);

        ENDPONT = getIntent().getStringExtra("ENDPOINT");
        TITLE = getIntent().getStringExtra("CATEGORYTITLE");
        ICON = getIntent().getIntExtra("CATEGORYICON", 0);
        ID = getIntent().getIntExtra("CATEGORYID", 0);

        mBackground = findViewById(R.id.activity_components_toolbar);
        mCategoryIcon = (ImageView) findViewById(R.id.activity_components_icon);
        mCategoryName = (TextView) findViewById(R.id.activity_components_name);
        mCategoryList = (ListView) findViewById(R.id.activity_components_list);

        mCategoryIcon.setImageDrawable(getResources().getDrawable(ICON));
        mCategoryName.setText(TITLE);

        Bitmap iconBitmap = BitmapFactory.decodeResource(getResources(), ICON);

        Palette palette = Palette.generate(iconBitmap);
        mBackground.setBackgroundColor(palette.getVibrantColor(getResources().getColor(R.color.primary)));
        getWindow().setStatusBarColor(palette.getDarkVibrantColor(getResources().getColor(R.color.primary_dark)));

        getCategoryComponents();
    }



    void getCategoryComponents() {
        final ProgressDialog dialog = ProgressDialog
                .show(this, "Espere...", "Obteniendo componentes", true);

        new AsyncTask<String, Void, ArrayList<Component>>() {

            @Override
            protected ArrayList<Component> doInBackground(String... params) {
                ComponentQuery query = new ComponentQuery(params[0]);

                return query.getComponents(params[1]);
            }

            @Override
            protected void onPostExecute(ArrayList<Component> components) {
                super.onPostExecute(components);

                if (components != null) {
                    componentsList = components;

                    //mCategoryList.setAdapter(new CompAdapter(getApplicationContext(), componentsList));

                    dialog.dismiss();
                }
            }
        }.execute(ENDPONT, ID.toString());
    }
}
