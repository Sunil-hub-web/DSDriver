package com.example.driversuvidha.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.driversuvidha.R;
import com.example.driversuvidha.modelclass.BookingHistory_ModelClass;
import com.example.driversuvidha.modelclass.UpcomingBooking_ModelClass;

import java.util.ArrayList;

public class BookingHistoryAdapter extends RecyclerView.Adapter<BookingHistoryAdapter.ViewHolder> {

    Context context;
    ArrayList<BookingHistory_ModelClass> booking;

    public BookingHistoryAdapter(Context context, ArrayList<BookingHistory_ModelClass> bookingHistory) {

        this.context = context;
        this.booking = bookingHistory;
    }

    @NonNull
    @Override
    public BookingHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookinghistory,parent,false);
        return new BookingHistoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  BookingHistoryAdapter.ViewHolder holder, int position) {

        BookingHistory_ModelClass booking_History = booking.get(position);

        holder.text_City.setText(booking_History.getCity());
        holder.text_ReportDate.setText(booking_History.getReportDate());
        holder.text_ReportTime.setText(booking_History.getReportTime());
        holder.text_Location.setText(booking_History.getLocation());
        holder.text_NoofDays.setText(booking_History.getNoofDays()+" "+ "Days");
        holder.text_DutyType.setText(booking_History.getDutyType());
        holder.text_Price.setText(booking_History.getRate());



    }

    @Override
    public int getItemCount() {
        return booking.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView text_ReportDate,text_ReportTime,text_City,text_Location,text_DutyType,text_NoofDays,text_Price;
        CardView book_Card;

        public ViewHolder(@NonNull  View itemView) {
            super(itemView);
            text_Location = itemView.findViewById(R.id.text_Location);
            text_ReportDate = itemView.findViewById(R.id.text_ReportDate);
            text_ReportTime = itemView.findViewById(R.id.text_ReportTime);
            text_City = itemView.findViewById(R.id.text_City);
            book_Card = itemView.findViewById(R.id.book_Card);
            text_NoofDays = itemView.findViewById(R.id.text_NoofDays);
            text_DutyType = itemView.findViewById(R.id.text_DutyType);
            text_Price = itemView.findViewById(R.id.text_Price);
        }
    }
}
