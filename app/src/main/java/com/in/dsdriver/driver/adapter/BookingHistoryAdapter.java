package com.in.dsdriver.driver.adapter;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.in.dsdriver.R;
import com.in.dsdriver.driver.modelclass.BookingHistory_ModelClass;
import com.in.dsdriver.extra.AppUrl;
import com.in.dsdriver.extra.SharedPrefManager_Driver;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class BookingHistoryAdapter extends RecyclerView.Adapter<BookingHistoryAdapter.ViewHolder> {

    Context context;
    ArrayList<BookingHistory_ModelClass> booking;
    int year, month, day, hour, minute;
    String date, time;

    public BookingHistoryAdapter(Context context, ArrayList<BookingHistory_ModelClass> bookingHistory) {

        this.context = context;
        this.booking = bookingHistory;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookinghistory, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        BookingHistory_ModelClass booking_History = booking.get(position);

        String reportdate = booking_History.getReportDate();

       /* SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");

        try {

            Date dateFromUser = fromUser.parse(reportdate); // Parse it to the exisitng date pattern and return Date type
            String dateMyFormat = myFormat.format(dateFromUser); // format it to the date pattern you prefer


        } catch (ParseException e) {
            e.printStackTrace();
        }*/

        holder.text_City.setText(booking_History.getCity());
        holder.text_ReportTime.setText(booking_History.getReportTime());
        holder.text_Location.setText(booking_History.getLocation());
        holder.text_DutyType.setText(booking_History.getDutyType());
        holder.text_Price.setText(booking_History.getRate());
        holder.text_ReportDate.setText(booking_History.getReportDate());

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        time = new SimpleDateFormat("hh:mm aa",Locale.getDefault()).format(new Date());

        holder.text_GuestRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.stopriderdialog);

                Button btn_Close = dialog.findViewById(R.id.btn_Close);
                Button btn_Submit = dialog.findViewById(R.id.btn_Submit);
                TextView text_endtime = dialog.findViewById(R.id.text_endtime);
                TextView text_enddate = dialog.findViewById(R.id.text_enddate);

                text_endtime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                int str_hour = hourOfDay;
                                int str_minute = minute;

                                Calendar calendar1 = Calendar.getInstance();

                                String sDate = date;

                                String[] string = sDate.split("-");

                                int sDay = Integer.parseInt(string[0]);

                                calendar1.set(Calendar.DAY_OF_MONTH, sDay);
                                calendar1.set(Calendar.HOUR_OF_DAY, str_hour);
                                calendar1.set(Calendar.MINUTE, str_minute);

                                text_endtime.setText(android.text.format.DateFormat.format("hh:mm aa", calendar1));

                                if (calendar1.getTimeInMillis() == Calendar.getInstance().getTimeInMillis()) {

                                    Toast.makeText(context, "Current Time selected", Toast.LENGTH_SHORT).show();

                                } else if (calendar1.getTimeInMillis() > Calendar.getInstance().getTimeInMillis()) {

                                    Toast.makeText(context, "Future time selected", Toast.LENGTH_SHORT).show();

                                } else {

                                    Toast.makeText(context, "Past time selected", Toast.LENGTH_LONG).show();
                                }


                            }
                        }, hour, minute, false);


                        timePickerDialog.show();
                    }
                });

                text_enddate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        DatePickerDialog datePickerDialog = new DatePickerDialog(
                                context, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                                month = month + 1;
                                String date = dayOfMonth + "-" + month + "-" + year;
                                //String date = year+"-"+month+"-"+day;
                                text_enddate.setText(date);
                            }
                        }, year, month, day);

                        //display previous selected date
                       // datePickerDialog.updateDate(year, month, day);

                        //disiable past date
                        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

                        datePickerDialog.show();


                  /*      DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                                new DatePickerDialog.OnDateSetListener() {

                                    @Override
                                    public void onDateSet(DatePicker view, int year,
                                                          int monthOfYear, int dayOfMonth) {

                                        text_enddate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                    }
                                }, year, month, day);

                        datePickerDialog.show();*/
                    }
                });

                btn_Submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String id = booking_History.getId();
                        String str_driverId = SharedPrefManager_Driver.getInstance(context).getUser().getDriverID();
                        String date = text_enddate.getText().toString().trim();
                        String time = text_endtime.getText().toString().trim();

                        gusetRequest(id,str_driverId,date,time);

                        dialog.dismiss();

                    }
                });

                btn_Close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                Window window = dialog.getWindow();
                dialog.setCanceledOnTouchOutside(false);
                dialog.setCancelable(false);
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                dialog.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return booking.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView text_ReportDate, text_ReportTime, text_City, text_Location, text_DutyType, text_NoofDays,
                text_Price, text_GuestRequest;
        CardView book_Card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            text_Location = itemView.findViewById(R.id.text_Location);
            text_ReportDate = itemView.findViewById(R.id.text_ReportDate);
            text_ReportTime = itemView.findViewById(R.id.text_ReportTime);
            text_City = itemView.findViewById(R.id.text_City);
            book_Card = itemView.findViewById(R.id.book_Card);
            text_NoofDays = itemView.findViewById(R.id.text_NoofDays);
            text_DutyType = itemView.findViewById(R.id.text_DutyType);
            text_Price = itemView.findViewById(R.id.text_Price);
            text_GuestRequest = itemView.findViewById(R.id.text_GuestRequest);

        }
    }

    public void gusetRequest(String bookingid, String driverid, String nextbookingdate, String nextreportingtime) {

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait....");
        progressDialog.setTitle("Guest Booking Request");
        progressDialog.show();

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("booking_id", bookingid);
            jsonObject.put("driver_id", driverid);
            jsonObject.put("next_booking_date", nextbookingdate);
            jsonObject.put("next_reporting_time", nextreportingtime);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppUrl.guestBooking, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                progressDialog.dismiss();

                try {

                    String status = response.getString("status");
                    String message = response.getString("message");

                    if (status.equals("true")) {

                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                error.printStackTrace();
                Toast.makeText(context, "" + error, Toast.LENGTH_SHORT).show();


            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.getCache().clear();
        requestQueue.add(jsonObjectRequest);

    }
}
