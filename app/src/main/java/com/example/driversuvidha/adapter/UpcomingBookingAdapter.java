package com.example.driversuvidha.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.driversuvidha.R;
import com.example.driversuvidha.fragment.BookingDetails;
import com.example.driversuvidha.fragment.UpcomingBookingFragment;
import com.example.driversuvidha.modelclass.UpcomingBooking_ModelClass;

import java.util.ArrayList;

public class UpcomingBookingAdapter extends RecyclerView.Adapter<UpcomingBookingAdapter.ViewHolder> {

    Context context;
    ArrayList<UpcomingBooking_ModelClass> upcoming;

    public UpcomingBookingAdapter(Context context, ArrayList<UpcomingBooking_ModelClass> upcomingBooking) {

        this.context = context;
        this.upcoming = upcomingBooking;
    }

    @NonNull
    @Override
    public UpcomingBookingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.upcomingbooking,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UpcomingBookingAdapter.ViewHolder holder, int position) {

        UpcomingBooking_ModelClass upcoming_Booking = upcoming.get(position);

        holder.text_City.setText(upcoming_Booking.getCity());
        holder.text_ReportDate.setText(upcoming_Booking.getReportDate());
        holder.text_ReportTime.setText(upcoming_Booking.getReportTime());
        holder.text_Location.setText(upcoming_Booking.getLocation());

        holder.book_Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UpcomingBookingFragment upcomingBookingFragment = new UpcomingBookingFragment();

                BookingDetails bookingDetails = new BookingDetails();
                FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.nav_host_fragment_activity_home_deshbord, bookingDetails).addToBackStack(null).commit();


            }
        });

    }

    @Override
    public int getItemCount() {
        return upcoming.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView text_ReportDate,text_ReportTime,text_City,text_Location;
        CardView book_Card;

        public ViewHolder(@NonNull  View itemView) {
            super(itemView);

            text_Location = itemView.findViewById(R.id.text_Location);
            text_ReportDate = itemView.findViewById(R.id.text_ReportDate);
            text_ReportTime = itemView.findViewById(R.id.text_ReportTime);
            text_City = itemView.findViewById(R.id.text_City);
            book_Card = itemView.findViewById(R.id.book_Card);
        }
    }
}
