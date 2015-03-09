package com.itesm.labs.labsuser.activities.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.itesm.labs.labsuser.R;
import com.melnykov.fab.FloatingActionButton;

public class ComponentsActivity extends ActionBarActivity {

    private String ENDPONT, TITLE;
    private Integer ICON, ID;
    private View mBackground;
    private ImageView mCategoryIcon;
    private TextView mCategoryName;
    private ListView mCategoryList;
    private FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_components);

        ENDPONT = getIntent().getStringExtra("ENDPOINT");
        TITLE = getIntent().getStringExtra("CATEGORYTITLE");
        ICON = getIntent().getIntExtra("CATEGORYICON",0);
        ID = getIntent().getIntExtra("CATEGORYID",0);

        mBackground = findViewById(R.id.activity_components_toolbar);
        mCategoryIcon = (ImageView) findViewById(R.id.activity_components_icon);
        mCategoryName = (TextView) findViewById(R.id.activity_components_name);
        mCategoryList = (ListView) findViewById(R.id.activity_components_list);
        mFab = (FloatingActionButton) findViewById(R.id.activity_components_fab);
        mFab.attachToListView(mCategoryList);

        mCategoryIcon.setImageDrawable(getResources().getDrawable(ICON));
        mCategoryName.setText(TITLE);

        Bitmap iconBitmap = BitmapFactory.decodeResource(getResources(),ICON);

        Palette palette = Palette.generate(iconBitmap);
        mBackground.setBackgroundColor(palette.getVibrantColor(getResources().getColor(R.color.primary)));
        getWindow().setStatusBarColor(palette.getDarkVibrantColor(getResources().getColor(R.color.primary_dark)));


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_components, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
