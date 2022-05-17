package com.in.dsdriver.driver.drivers;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Toast;

import com.in.dsdriver.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.in.dsdriver.driver.fragment.BookingHistory;
import com.in.dsdriver.driver.fragment.HomeFragment;
import com.in.dsdriver.driver.fragment.ProfileFragment;
import com.in.dsdriver.driver.fragment.UpcomingBookingFragment;
import com.in.dsdriver.driver.fragment.WalletFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class HomeDeshbord extends AppCompatActivity {

    public static BottomNavigationView navView;
    private Boolean exit = false;
    public static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home_deshbord);

        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
       /* AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_Profile, R.id.navigation_upcomingbooking,R.id.navigation_wallet,R.id.navigation_bookingHistory)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_home_deshbord);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);*/

        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_home_deshbord,new HomeFragment(),"HomeFragment").addToBackStack(null).commit();
        HomeFragment test = (HomeFragment) getSupportFragmentManager().findFragmentByTag("HomeFragment");

        navView.setSelectedItemId(R.id.navigation_home);

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment selectedFragment = null;

                switch (item.getItemId()){

                    case R.id.navigation_home:

                        selectedFragment = new HomeFragment();

                        break;

                    case R.id.navigation_Profile:

                        selectedFragment = new ProfileFragment();

                        break;

                    case R.id.navigation_upcomingbooking:

                        selectedFragment = new UpcomingBookingFragment();

                        break;

                    case R.id.navigation_wallet:

                        selectedFragment = new WalletFragment();

                        break;

                    case R.id.navigation_bookingHistory:

                        selectedFragment = new BookingHistory();

                        break;


                }
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_home_deshbord,selectedFragment).commit();

                return true;
            }
        });


    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        HomeFragment test = (HomeFragment) getSupportFragmentManager().findFragmentByTag("HomeFragment");

        if (test != null && test.isVisible()) {

            if (exit) {
                finish(); // finish activity
            } else {
                Toast.makeText(this, "Press Back again to Exit.",
                        Toast.LENGTH_SHORT).show();
                exit = true;
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {

                        Intent a = new Intent(Intent.ACTION_MAIN);
                        a.addCategory(Intent.CATEGORY_HOME);
                        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(a);
                    }
                }, 4 * 1000);
            }
        }
        else {

            getSupportFragmentManager().beginTransaction().
                    replace(R.id.nav_host_fragment_activity_home_deshbord,new HomeFragment(),"HomeFragment").commit();

        }
    }
}