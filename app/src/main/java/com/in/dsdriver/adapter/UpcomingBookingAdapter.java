package com.in.dsdriver.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.in.dsdriver.R;
import com.in.dsdriver.fragment.BookingDetails;
import com.in.dsdriver.fragment.UpcomingBookingFragment;
import com.in.dsdriver.modelclass.UpcomingBooking_ModelClass;

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


                FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();

                BookingDetails bookingDetails = new BookingDetails();

                Bundle args = new Bundle();
                args.putString("param1", upcoming_Booking.getDuty_type());
                args.putString("param2", upcoming_Booking.getAddress_id());
                args.putString("param3", upcoming_Booking.getReportTime());
                args.putString("param4", upcoming_Booking.getCar_detail());
                //args.putString("param5", upcoming_Booking.getAddress_id());
                args.putString("param5", upcoming_Booking.getCustomer_id());

                bookingDetails.setArguments(args);

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
