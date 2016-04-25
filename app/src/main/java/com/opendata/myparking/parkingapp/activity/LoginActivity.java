package com.opendata.myparking.parkingapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.opendata.myparking.parkingapp.R;

public class LoginActivity extends AppCompatActivity {

    private Button buttonRegistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        buttonRegistration = (Button) findViewById(R.id.buttonRegistration);

        buttonRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), RegisterActivity.class);
                //startActivity(intent);

                startActivityForResult(intent, 0);
                finish();

            }
        });

    }

}