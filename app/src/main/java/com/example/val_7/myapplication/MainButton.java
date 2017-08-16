package com.example.val_7.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by val-7 on 14/08/2017.
 */

public class MainButton extends Activity implements View.OnClickListener{
    private TextView textResult;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textResult = ((TextView) findViewById(R.id.textResult));
        textResult.setText("Oui");

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
    }

    public void onClick(View v) {
        textResult.setText("Arthur est vraiment pd");
    }

}
