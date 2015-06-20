package com.itesm.labs.labsuser.activities.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.activities.rest.models.User;
import com.itesm.labs.labsuser.activities.rest.service.CategoryService;
import com.itesm.labs.labsuser.activities.rest.service.UserService;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;


public class LoginActivity extends ActionBarActivity {

    private Button loginBtn, signupBtn;
    private EditText userMat, userPass;

    private static final String URL = "http://labs.chi.itesm.mx:8080/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userMat = (EditText) findViewById(R.id.login_user_id);
        userPass = (EditText) findViewById(R.id.login_user_pass);

        loginBtn = (Button) findViewById(R.id.login_button);
        signupBtn = (Button) findViewById(R.id.signup_button);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin(userMat.getText().toString());
            }
        });
    }

    private void doLogin(String userId){
        try {
            new AsyncTask<String, Void, User>() {
                @Override
                protected User doInBackground(String... params) {
                    try {
                        Gson gson = new GsonBuilder().create();

                        RestAdapter restAdapter = new RestAdapter.Builder()
                                .setEndpoint(URL)
                                .setConverter(new GsonConverter(gson))
                                .build();

                        UserService service = restAdapter.create(UserService.class);

                        return service.getUser(params[0]);
                    } catch (Exception ex){
                        Snackbar.make(findViewById(android.R.id.content), "Error", Snackbar.LENGTH_SHORT)
                                .show();
                        return null;
                    }
                }

                @Override
                protected void onPostExecute(User user) {
                    super.onPostExecute(user);

                    if(user != null) {
                        Intent intent = new Intent(getApplicationContext(), LaboratoriesActivity.class);
                        intent.putExtra("USERID", user.getUserId());
                        intent.putExtra("ALLOWEDLABS", user.getAllowedLabs());
                        startActivity(intent);
                        overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_in_bottom);
                    }
                }
            }.execute(userId);

        } catch (Exception ex){
            Snackbar.make(findViewById(android.R.id.content), "Error", Snackbar.LENGTH_SHORT)
                    .show();
        }
    }
}
