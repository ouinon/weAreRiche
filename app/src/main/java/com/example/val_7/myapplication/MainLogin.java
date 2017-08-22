package com.example.val_7.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by val-7 on 22/08/2017.
 */

public class MainLogin extends AppCompatActivity {

    private Button signin;
    private Button signup;

    private EditText login;
    private EditText password;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);

        signin = ((Button) findViewById(R.id.signin));
        signup = ((Button) findViewById(R.id.signup));

        login    = ((EditText) findViewById(R.id.login));
        password = ((EditText) findViewById(R.id.password));

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainLogin.this, MainActivity.class));
            }
        });

    }
}
