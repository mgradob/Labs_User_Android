package com.itesm.labs.labsuser.app.commons.views.activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.bases.BaseActivity;
import com.itesm.labs.labsuser.app.commons.contracts.IDialogContract;
import com.itesm.labs.labsuser.app.commons.views.fragments.ChangePasswordDialogFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AccountActivity extends BaseActivity implements IDialogContract {

    @Bind(R.id.activity_register_toolbar)
    Toolbar activityRegisterToolbar;
    @Bind(R.id.account_full_name)
    TextView accountFullName;
    @Bind(R.id.account_user_id)
    TextView accountUserId;
    @Bind(R.id.account_career)
    TextView accountCareer;
    @Bind(R.id.account_mail)
    TextView accountMail;
    @Bind(R.id.account_reset_password)
    TextView accountResetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        ButterKnife.bind(this);

        setSupportActionBar(activityRegisterToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);

        setupUi();
    }

    @Override
    public void setupUi() {
        fillUserInfo();
    }

    private void fillUserInfo() {
        accountFullName.setText(String.format(
                getString(R.string.account_full_name),
                mLabsPreferences.getUserName(),
                mLabsPreferences.getUserLastName1(),
                mLabsPreferences.getUserLastName2())
        );

        accountUserId.setText(String.format(
                getString(R.string.account_user_id),
                mLabsPreferences.getUserId()
        ));

        accountCareer.setText(String.format(
                getString(R.string.account_career),
                mLabsPreferences.getUserCareer()
        ));

        accountMail.setText(String.format(
                getString(R.string.account_mail),
                mLabsPreferences.getUserMail()
        ));
    }

    @OnClick(R.id.account_reset_password)
    public void onClick() {
        ChangePasswordDialogFragment fragment = new ChangePasswordDialogFragment();
        fragment.setDialogContract(this);
        fragment.show(getSupportFragmentManager(), null);
    }

    @Override
    public void onActionSuccess() {
        logoutUser();

        Toast.makeText(mContext, R.string.event_password_changed, Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void onActionFailed() {
        Snackbar.make(findViewById(R.id.activity_account), R.string.event_error_network, Snackbar.LENGTH_LONG)
                .show();
    }
}
