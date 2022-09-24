package com.in.dsdriver.driver.adapter;

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
import com.in.dsdriver.driver.fragment.BookingDetails;
import com.in.dsdriver.driver.modelclass.UpcomingBooking_ModelClass;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

        String reportdate = upcoming_Booking.getDate();
        holder.text_ReportDate.setText(upcoming_Booking.getDate());

      /*  SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");

        try {

            Date dateFromUser = fromUser.parse(reportdate); // Parse it to the exisitng date pattern and return Date type
            String dateMyFormat = myFormat.format(dateFromUser); // format it to the date pattern you prefer


        } catch (ParseException e) {
            e.printStackTrace();
        }
*/
        holder.text_City.setText(upcoming_Booking.getCity());

        holder.text_ReportTime.setText(upcoming_Booking.getTime());
        holder.text_Location.setText(upcoming_Booking.getLocality());

        holder.book_Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();

                BookingDetails bookingDetails = new BookingDetails();

                Bundle args = new Bundle();

                args.putString("bookingid", upcoming_Booking.getBooking_id());
                args.putString("bookingType", upcoming_Booking.getBookingType());
                args.putString("customerName", upcoming_Booking.getCustomerName());
                args.putString("time", upcoming_Booking.getTime());
                args.putString("CarDetails", upcoming_Booking.getCarDetails());
                args.putString("date", upcoming_Booking.getDate());
                args.putString("city", upcoming_Booking.getCity());
                args.putString("day", upcoming_Booking.getDay());
                args.putString("shift", upcoming_Booking.getShift());
                args.putString("DutyHours", upcoming_Booking.getDutyHours());
                args.putString("DropLoc", upcoming_Booking.getDropLoc());
                args.putString("CarDetails", upcoming_Booking.getCarDetails());
                args.putString("Remarks", upcoming_Booking.getRemarks());
                args.putString("Charges", upcoming_Booking.getCharges());
                args.putString("OTHours", upcoming_Booking.getOTHours());
                args.putString("OTAmount", upcoming_Booking.getOTAmount());
                args.putString("TotalAmount", upcoming_Booking.getTotalAmount());
                args.putString("address", upcoming_Booking.getAddress());
                args.putString("Locality", upcoming_Booking.getLocality());
                args.putString("Landmark", upcoming_Booking.getLandmark());
                args.putString("endtime", upcoming_Booking.getEnd_time());
                args.putString("return_date", upcoming_Booking.getReturn_date());
                args.putString("customer_mobile", upcoming_Booking.getCustomer_mobile());

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
