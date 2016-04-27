package com.itesm.labs.labsuser.app.commons.views.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.bases.BaseActivity;
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
    @Bind(R.id.login_remember_me)
    AppCompatCheckBox loginRememberMe;


    private LoginActivityPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        mPresenter = new LoginActivityPresenter(this);

        setupUi();
    }

    @Override
    protected void onResume() {
        super.onResume();

        loginBtn.setEnabled(true);
    }

    @Override
    public void setupUi() {
        if (mLabsPreferences.getRememberInfo()) {
            userMat.setText(mLabsPreferences.getUserId());
            userPass.setText(mLabsPreferences.getUserPass());
        }

        setupStatusBar(getResources().getColor(R.color.primary));
    }

    @OnClick(R.id.login_button)
    void doLogin() {
        loginBtn.setEnabled(false);

        if (loginRememberMe.isChecked()) mLabsPreferences.putRememberInfo(true);
        else mLabsPreferences.putRememberInfo(false);

        String userM = userMat.getText().toString();
        String userP = userPass.getText().toString();

        mLabsPreferences.putUserId(userM);
        mLabsPreferences.putUserPass(userP);

        mPresenter.loginUser();
    }

    public void goToLabsView() {
        Log.d(TAG, "Going to labs view");
        Intent intent = new Intent(mContext, LabsActivity.class);
        startActivity(intent);
    }

    public void displayLoginError() {
        Snackbar.make(findViewById(R.id.login_activity), R.string.login_snackbar_error_content,
                Snackbar.LENGTH_LONG)
                .show();

        loginBtn.setEnabled(true);
    }
}
