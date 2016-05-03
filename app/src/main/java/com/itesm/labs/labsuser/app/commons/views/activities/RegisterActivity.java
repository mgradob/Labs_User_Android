package com.itesm.labs.labsuser.app.commons.views.activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.bases.BaseActivity;
import com.itesm.labs.labsuser.app.commons.views.presenters.RegisterActivityPresenter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {

    @Bind(R.id.activity_register_toolbar)
    Toolbar activityRegisterToolbar;
    @Bind(R.id.activity_register_mat)
    AppCompatEditText activityRegisterMat;
    @Bind(R.id.activity_register_name)
    AppCompatEditText activityRegisterName;
    @Bind(R.id.activity_register_ap_1)
    AppCompatEditText activityRegisterAp1;
    @Bind(R.id.activity_register_ap_2)
    AppCompatEditText activityRegisterAp2;
    @Bind(R.id.activity_register_career)
    AppCompatEditText activityRegisterCareer;
    @Bind(R.id.activity_register_password)
    AppCompatEditText activityRegisterPassword;
    @Bind(R.id.activity_register_done)
    Button activityRegisterDone;

    private RegisterActivityPresenter mPresenter;

    private String regexUserId = "^[alAL][\\d]{8}$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        mPresenter = new RegisterActivityPresenter(this);
    }

    @Override
    public void setupUi() {
        setSupportActionBar(activityRegisterToolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @OnClick(R.id.activity_register_done)
    public void onClick() {
        String userId = activityRegisterMat.getText().toString();
        if (userId.matches(regexUserId)) {
            userId = userId.charAt(0) == 'a' ? userId.replace('a', 'A') :
                    userId.charAt(0) == 'l' ? userId.replace('l', 'L') : userId;
        } else {
            showUserIdError();
            return;
        }

        mPresenter.registerUser(
                userId,
                activityRegisterName.getText().toString(),
                activityRegisterAp1.getText().toString(),
                activityRegisterAp2.getText().toString(),
                activityRegisterCareer.getText().toString(),
                activityRegisterPassword.getText().toString()
        );
    }

    public void showUserIdError() {
        Snackbar.make(findViewById(R.id.activity_register), "Matrícula incorrecta", Snackbar.LENGTH_LONG)
                .show();
    }
}
