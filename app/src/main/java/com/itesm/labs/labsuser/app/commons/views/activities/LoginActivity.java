package com.itesm.labs.labsuser.app.commons.views.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

    @Bind(R.id.login_user_id)
    EditText loginUserId;
    @Bind(R.id.login_user_pass)
    EditText loginUserPass;
    @Bind(R.id.login_button)
    Button loginButton;
    @Bind(R.id.login_remember_me)
    AppCompatCheckBox loginRememberMe;
    @Bind(R.id.login_register)
    TextView loginRegister;

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

        loginButton.setEnabled(true);
    }

    @Override
    public void setupUi() {
        if (mLabsPreferences.getRememberInfo()) {
            loginUserId.setText(mLabsPreferences.getUserId());
            loginUserPass.setText(mLabsPreferences.getUserPass());

            doLogin();
        }

        setupStatusBar(getResources().getColor(R.color.primary));
    }

    @OnClick({R.id.login_button, R.id.login_register})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button:
                doLogin();
                break;
            case R.id.login_register:
                startActivity(new Intent(mContext, RegisterActivity.class));
                break;
        }
    }

    void doLogin() {
        showProgressDialog(R.string.login_dialog_title, R.string.login_dialog_content);

        loginButton.setEnabled(false);

        if (loginRememberMe.isChecked()) mLabsPreferences.putRememberInfo(true);
        else mLabsPreferences.putRememberInfo(false);

        String userM = loginUserId.getText().toString();
        String userP = loginUserPass.getText().toString();

        mLabsPreferences.putUserId(userM);
        mLabsPreferences.putUserPass(userP);

        mPresenter.loginUser();
    }

    public void goToLabsView() {
        Log.d(TAG, "Going to labs view");

        dismissProgressDialog();

        Intent intent = new Intent(mContext, LabsActivity.class);
        startActivity(intent);
    }

    public void displayLoginError() {
        dismissProgressDialog();

        Snackbar.make(findViewById(R.id.login_activity), R.string.login_snackbar_error_content,
                Snackbar.LENGTH_LONG)
                .show();

        loginButton.setEnabled(true);
    }
}
