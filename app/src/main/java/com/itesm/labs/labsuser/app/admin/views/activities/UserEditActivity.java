package com.itesm.labs.labsuser.app.admin.views.activities;

import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.admin.views.presenters.UserEditPresenter;
import com.itesm.labs.labsuser.app.bases.BaseActivity;
import com.itesm.labs.labsuser.app.commons.events.UIDEvent;
import com.mgb.labsapi.models.User;
import com.squareup.otto.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mgradob on 4/13/16.
 */
public class UserEditActivity extends BaseActivity {

    public static final String EXTRA_USER_ID = "USER_ID";
    
    private static final String TAG = UserEditActivity.class.getSimpleName();

    @Bind(R.id.activity_user_edit_toolbar)
    Toolbar activityUserEditToolbar;
    @Bind(R.id.activity_user_edit_mat)
    AppCompatEditText activityUserEditMat;
    @Bind(R.id.activity_user_edit_name)
    AppCompatEditText activityUserEditName;
    @Bind(R.id.activity_user_edit_ap_1)
    AppCompatEditText activityUserEditAp1;
    @Bind(R.id.activity_user_edit_ap_2)
    AppCompatEditText activityUserEditAp2;
    @Bind(R.id.activity_user_edit_career)
    AppCompatEditText activityUserEditCareer;
    @Bind(R.id.activity_user_edit_uid)
    TextView activityUserEditUid;
    @Bind(R.id.activity_user_edit_update)
    Button activityUserEditUpdate;
    @Bind(R.id.activity_user_edit_cancel)
    Button activityUserEditCancel;
    @Bind(R.id.activity_user_edit_done)
    Button activityUserEditDone;

    UserEditPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit);
        ButterKnife.bind(this);

        setSupportActionBar(activityUserEditToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String userId = getIntent().getStringExtra(EXTRA_USER_ID);

        mPresenter = new UserEditPresenter(this);
        mPresenter.setupNfc();
        mPresenter.getUser(userId);
    }

    @Override
    public void setupUi() {
    }

    public void fillUserInfo(User user) {
        activityUserEditMat.setText(user.getUserId());
        activityUserEditName.setText(user.getUserName());
        activityUserEditAp1.setText(user.getUserLastName1());
        activityUserEditAp2.setText(user.getUserLastName2());
        activityUserEditCareer.setText(user.getUserCareer());
        activityUserEditUid.setText(String.format(getString(R.string.signup_uid), String.valueOf(user.getUserUid())));
    }

    @OnClick({R.id.activity_user_edit_update, R.id.activity_user_edit_cancel, R.id.activity_user_edit_done})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_user_edit_update:
                break;
            case R.id.activity_user_edit_cancel:
                finish();
                break;
            case R.id.activity_user_edit_done:
                mPresenter.doEdit(
                        activityUserEditMat.getText().toString(),
                        activityUserEditName.getText().toString(),
                        activityUserEditAp1.getText().toString(),
                        activityUserEditAp2.getText().toString(),
                        activityUserEditCareer.getText().toString(),
                        Long.parseLong(activityUserEditUid.getText().toString())
                );
                break;
        }
    }

    @Subscribe
    public void onUIDEvent(UIDEvent event) {
        if (event != null)
            activityUserEditUid.setText(String.format(getString(R.string.signup_uid), String.valueOf(event.getUID())));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.enableNfc();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.disableNfc();
    }
}
