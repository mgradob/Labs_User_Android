package com.itesm.labs.labsuser.app.commons.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.application.AppConstants;
import com.itesm.labs.labsuser.app.bases.LabsBaseActivity;
import com.itesm.labs.labsuser.app.commons.services.BackgroundService;
import com.mgb.labsapi.clients.UserClient;
import com.mgb.labsapi.models.Auth;
import com.mgb.labsapi.models.LoginBody;
import com.mgb.labsapi.models.User;

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

    private BackgroundService mBackgroundService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        userMat.setText(mLabsPreferences.getUserId());
        userPass.setText(mLabsPreferences.getUserPass());

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

        mLabsPreferences.putUserId(userM);
        mLabsPreferences.putUserPass(userP);

        getToken();
    }

    private void getToken() {
        mUserClient.loginUser(new LoginBody.Builder()
                .setId_student(mLabsPreferences.getUserId())
                .setPassword(mLabsPreferences.getUserPass()).build())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Auth>() {
                    @Override
                    public void onStart() {
                        Log.d(TAG, "Login started");

                        showMaterialDialog(R.string.login_dialog_title, R.string.login_dialog_content);
                    }

                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Task complete, got token");

                        getUser();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Task error: " + e.getMessage());

                        dismissMaterialDialog();

                        Snackbar.make(findViewById(R.id.login_activity),
                                getResources().getString(R.string.login_snackbar_error_content),
                                Snackbar.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(Auth token) {
                        Log.d(TAG, "Token: " + token);

                        if (token == null)
                            throw new NullPointerException("Token was null");


                        mLabsPreferences.putToken(token.getAuthToken());
                    }
                });
    }

    private void getUser() {
        mUserClient.getUser(mLabsPreferences.getToken(), mLabsPreferences.getUserId())
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

                        dismissMaterialDialog();

                        Intent intent = new Intent(getApplicationContext(), LaboratoriesActivity.class);
                        intent.putExtra(AppConstants.INTENT_USER_ID, mLabsPreferences.getUserId());
                        intent.putStringArrayListExtra(AppConstants.INTENT_ALLOWED_LABS, mLabsPreferences.getUserAllowedLabs());
                        startActivity(intent);
                        overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_in_bottom);

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Task error: " + e.getMessage());


                        Snackbar.make(findViewById(R.id.login_activity),
                                getResources().getString(R.string.login_snackbar_error_content),
                                Snackbar.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(User user) {
                        Log.d(TAG, "User: " + user);

                        if (user == null)
                            throw new NullPointerException("User was null");

                        mLabsPreferences.putUser(user);
                    }
                });
    }
}
