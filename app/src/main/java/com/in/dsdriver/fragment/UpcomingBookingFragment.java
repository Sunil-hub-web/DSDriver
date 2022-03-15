package com.in.dsdriver.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.in.dsdriver.R;
import com.in.dsdriver.adapter.UpcomingBookingAdapter;
import com.in.dsdriver.modelclass.UpcomingBooking_ModelClass;

import java.util.ArrayList;


public class UpcomingBookingFragment extends Fragment {

    RecyclerView recyclerUpcomingBooking;
    LinearLayoutManager linearLayoutManager;
    UpcomingBookingAdapter upcomingBookingAdapter;
    ArrayList<UpcomingBooking_ModelClass> upcomingBooking = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater,
                             @Nullable  ViewGroup container,
                             @Nullable  Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_upcomingbooking,container,false);

        recyclerUpcomingBooking = view.findViewById(R.id.recyclerUpcomingBooking);

        upcomingBooking.add(new UpcomingBooking_ModelClass("05/01/2022","05:15 PM","Mumbai","Andhera East"));
        upcomingBooking.add(new UpcomingBooking_ModelClass("05/01/2022","05:15 PM","Mumbai","Andhera East"));
        upcomingBooking.add(new UpcomingBooking_ModelClass("05/01/2022","05:15 PM","Mumbai","Andhera East"));

        linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        upcomingBookingAdapter = new UpcomingBookingAdapter(getContext(),upcomingBooking);
        recyclerUpcomingBooking.setLayoutManager(linearLayoutManager);
        recyclerUpcomingBooking.setHasFixedSize(true);
        recyclerUpcomingBooking.setAdapter(upcomingBookingAdapter);

        return view;
    }
}