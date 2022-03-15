package com.in.dsdriver.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.in.dsdriver.HomeDeshbord;
import com.in.dsdriver.R;


public class HomeFragment extends Fragment {

    Button btn_TodayReportDetails,btn_WorkDetails,btn_MyProfile,btn_CompleteDuty,btn_Wallet;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable  ViewGroup container,
                             @Nullable  Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home,container,false);

        btn_TodayReportDetails = view.findViewById(R.id.btn_TodayReportDetails);
        btn_WorkDetails = view.findViewById(R.id.btn_WorkDetails);
        btn_MyProfile = view.findViewById(R.id.btn_MyProfile);
        btn_CompleteDuty = view.findViewById(R.id.btn_CompleteDuty);
        btn_Wallet = view.findViewById(R.id.btn_Wallet);

        btn_WorkDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TodayReport todayReport = new TodayReport();
                FragmentTransaction transaction =  getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment_activity_home_deshbord, todayReport);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        btn_Wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                WalletFragment walletFragment = new WalletFragment();
                FragmentTransaction transaction =  getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment_activity_home_deshbord, walletFragment);
                transaction.addToBackStack(null);
                transaction.commit();

                HomeDeshbord. navView.setSelectedItemId(R.id.navigation_wallet);

            }
        });

        btn_MyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProfileFragment profileFragment = new ProfileFragment();
                FragmentTransaction transaction =  getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment_activity_home_deshbord, profileFragment);
                transaction.addToBackStack(null);
                transaction.commit();

                HomeDeshbord. navView.setSelectedItemId(R.id.navigation_Profile);
            }
        });

        btn_TodayReportDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UpcomingBookingFragment upcomingBookingFragment = new UpcomingBookingFragment();
                FragmentTransaction transaction =  getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment_activity_home_deshbord, upcomingBookingFragment);
                transaction.addToBackStack(null);
                transaction.commit();

                HomeDeshbord. navView.setSelectedItemId(R.id.navigation_upcomingbooking);

            }
        });

        btn_CompleteDuty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BookingHistory bookingHistory = new BookingHistory();
                FragmentTransaction transaction =  getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment_activity_home_deshbord, bookingHistory);
                transaction.addToBackStack(null);
                transaction.commit();

                HomeDeshbord. navView.setSelectedItemId(R.id.navigation_bookingHistory);
            }
        });


        return view;

    }
}