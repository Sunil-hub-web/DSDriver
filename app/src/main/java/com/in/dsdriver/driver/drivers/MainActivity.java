package com.in.dsdriver.driver.drivers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.in.dsdriver.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}