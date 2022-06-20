package com.in.dsdriver.cabowner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.in.dsdriver.R;
import com.in.dsdriver.cabowner.fragment.BookingHistoryFragment;
import com.in.dsdriver.cabowner.fragment.FragmentHome;
import com.in.dsdriver.cabowner.fragment.FragmentProfile;
import com.in.dsdriver.cabowner.fragment.FragmentWallet;
import com.in.dsdriver.cabowner.fragment.MyReportFragment;

public class DeshBoard extends AppCompatActivity {

    public static BottomNavigationView navView;
    private Boolean exit = false;
    public static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_desh_board);

        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navView = findViewById(R.id.nav_view);

        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_home_deshbord,new FragmentHome(),"HomeFragment").addToBackStack(null).commit();
        FragmentHome test = (FragmentHome) getSupportFragmentManager().findFragmentByTag("HomeFragment");

        navView.setSelectedItemId(R.id.navigation_home);

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment selectedFragment = null;

                switch (item.getItemId()){

                    case R.id.navigation_home:

                        selectedFragment = new FragmentHome();

                        break;

                    case R.id.navigation_Profile:

                        selectedFragment = new FragmentProfile();

                        break;

                    case R.id.navigation_upcomingbooking:

                        selectedFragment = new MyReportFragment();

                        break;

                    case R.id.navigation_wallet:

                        selectedFragment = new FragmentWallet();

                        break;

                    case R.id.navigation_bookingHistory:

                        selectedFragment = new BookingHistoryFragment();

                        break;

                }

                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_home_deshbord,selectedFragment).commit();

                return true;
            }
        });

    }
}