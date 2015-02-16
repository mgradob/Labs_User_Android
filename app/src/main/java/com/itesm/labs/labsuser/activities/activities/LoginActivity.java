package com.itesm.labs.labsuser.activities.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.itesm.labs.labsuser.R;


public class LoginActivity extends ActionBarActivity {

    private Button loginBtn, signupBtn;
    private EditText userMat, userPass;

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
                Intent intent = new Intent(getApplicationContext(), LaboratoriosActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_in_bottom);
            }
        });
    }
}
