package com.itesm.labs.labsuser.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.application.AppConstants;
import com.itesm.labs.labsuser.app.bases.LabsBaseActivity;
import com.itesm.labs.labsuser.app.rest.clients.UserClient;
import com.itesm.labs.labsuser.app.rest.models.LoginUser;
import com.itesm.labs.labsuser.app.rest.models.User;
import com.itesm.labs.labsuser.app.services.BackgroundService;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by mgradob on 10/26/15.
 */
public class LoginActivity extends LabsBaseActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    @Bind(R.id.login_button)
    Button loginBtn;

    @Bind(R.id.signup_button)
    Button signupBtn;

    @Bind(R.id.login_user_id)
    EditText userMat;

    @Bind(R.id.login_user_pass)
    EditText userPass;

    @Inject
    UserClient mUserClient;

    MaterialDialog mDialog;

    private BackgroundService mBackgroundService;

    private LoginUser mLoginUser = new LoginUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        userMat.setText(mSharedPreferences.getString(AppConstants.PREFERENCES_KEY_USER_ID, ""));
        userPass.setText(mSharedPreferences.getString(AppConstants.PREFERENCES_KEY_USER_PASS, ""));

//        mBackgroundService = new BackgroundService();
//        Intent backgroundServiceIntent = new Intent();
//        backgroundServiceIntent.putExtra(getResources().getString(R.string.intent_endpoint), )
//        mBackgroundService.startService(getResources().getString(R.string.intent_user_id));
    }

    @OnClick(R.id.signup_button)
    void doSignup() {
        Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
        startActivity(intent);
    }

    @OnClick(R.id.login_button)
    void doLogin() {
        String userM = userMat.getText().toString();
        String userP = userPass.getText().toString();

        mLoginUser.setId_student(userM);
        mLoginUser.setPassword(userP);

        mSharedPreferences.edit()
                .putString(AppConstants.PREFERENCES_KEY_USER_ID, userM)
                .putString(AppConstants.PREFERENCES_KEY_USER_PASS, userP)
                .apply();

        getToken();
    }

    private void getToken() {
        mUserClient.loginUser(mLoginUser)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onStart() {
                        Log.d(TAG, "Login started");

                        showDialog(true);
                    }

                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Task complete, got token");

                        getUser();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Task error: " + e.getMessage());

                        showDialog(false);

                        Snackbar.make(findViewById(R.id.login_activity),
                                getResources().getString(R.string.login_snackbar_error_content),
                                Snackbar.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(String token) {
                        Log.d(TAG, "Token: " + token);

                        if (token == null)
                            throw new NullPointerException("Token was null");


                        mSharedPreferences.edit()
                                .putString(AppConstants.PREFERENCES_KEY_USER_TOKEN, "Token " + token)
                                .apply();
                    }
                });
    }

    private void getUser() {
        mUserClient.getUser(mSharedPreferences.getString(AppConstants.PREFERENCES_KEY_USER_TOKEN, ""),
                mSharedPreferences.getString(AppConstants.PREFERENCES_KEY_USER_ID, ""))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onStart() {
                        Log.d(TAG, "Task get user");
                    }

                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Login complete, got user");

                        showDialog(false);

                        Intent intent = new Intent(getApplicationContext(), LaboratoriesActivity.class);
                        intent.putExtra(AppConstants.INTENT_USER_ID, mAppGlobals.getUser().getUserId());
                        intent.putExtra(AppConstants.INTENT_ALLOWED_LABS, mAppGlobals.getUser().getAllowedLabs());
                        startActivity(intent);
                        overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_in_bottom);

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Task error: " + e.getMessage());

                        showDialog(false);

                        Snackbar.make(findViewById(R.id.login_activity),
                                getResources().getString(R.string.login_snackbar_error_content),
                                Snackbar.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(User user) {
                        Log.d(TAG, "User: " + user);

                        if (user == null)
                            throw new NullPointerException("User was null");

                        mAppGlobals.setUser(user);
                    }
                });
    }

    private void showDialog(boolean show) {
        if (show) {
            mDialog = new MaterialDialog.Builder(this)
                    .title(getResources().getString(R.string.login_dialog_title))
                    .content(getResources().getString(R.string.login_dialog_content))
                    .progress(true, 100)
                    .show();
        } else {
            mDialog.dismiss();
        }
    }
}
