package com.itesm.labs.labsuser.app.commons.views.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.widget.Button;
import android.widget.EditText;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.bases.BaseActivity;
import com.itesm.labs.labsuser.app.commons.events.DismissDialogEvent;
import com.itesm.labs.labsuser.app.commons.events.ShowDialogEvent;
import com.itesm.labs.labsuser.app.commons.views.presenters.LoginActivityPresenter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mgradob on 10/26/15.
 */
public class LoginActivity extends BaseActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    @Bind(R.id.login_button)
    Button loginBtn;
    @Bind(R.id.login_user_id)
    EditText userMat;
    @Bind(R.id.login_user_pass)
    EditText userPass;

    private LoginActivityPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_login);

        ButterKnife.bind(this);

        mPresenter = new LoginActivityPresenter(this);

        setupUi();
    }

    @Override
    public void setupUi() {
        userMat.setText(mLabsPreferences.getUserId());
        userPass.setText(mLabsPreferences.getUserPass());
    }

    @OnClick(R.id.login_button)
    void doLogin() {
        String userM = userMat.getText().toString();
        String userP = userPass.getText().toString();

        mLabsPreferences.putUserId(userM);
        mLabsPreferences.putUserPass(userP);

        mPresenter.loginUser();
    }

    @Override
    public void onShowDialogEvent(ShowDialogEvent event) {
    }

    @Override
    public void onDismissDialogEvent(DismissDialogEvent event) {
    }

    public void goToLabsView() {
        Intent intent = new Intent(getApplicationContext(), LabsActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_from_top, R.anim.abc_slide_in_bottom);
    }

    public void displayLoginError() {
        Snackbar.make(findViewById(R.id.login_activity), R.string.login_snackbar_error_content,
                Snackbar.LENGTH_LONG)
                .show();
    }
}
