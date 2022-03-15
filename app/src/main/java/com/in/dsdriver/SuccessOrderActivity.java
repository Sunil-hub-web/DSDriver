package com.in.dsdriver;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class SuccessOrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_order);

        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}