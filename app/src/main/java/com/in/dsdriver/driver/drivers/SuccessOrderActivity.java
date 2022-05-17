package com.in.dsdriver.driver.drivers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.in.dsdriver.R;

public class SuccessOrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_order);

        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}