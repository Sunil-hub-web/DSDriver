package com.in.dsdriver.driver.fragment;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
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
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.in.dsdriver.R;
import com.in.dsdriver.extra.AppUrl;
import com.in.dsdriver.extra.SharedPrefManager_Driver;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class BookingDetails extends Fragment {

    TextView text_BookingType,text_CustomerName,text_Address,text_Time,text_Date,text_Shift,text_Day,text_DutyHours,
            text_DropLoc,text_CarDetails,text_Remarks,text_Charges,text_OTHours,text_OTAmount,text_TotalAmount,
            text_StopRider,text_CallCustomer,text_enddate,textShift,textDay,textDutyHours,text_Locality,
            text_Landmark;

    String str_BookingType,str_CustomerName,str_Address,str_Time,str_Date,str_Shift,str_Day,
            str_DutyHours, str_DropLoc,str_CarDetails,str_Remarks,str_Charges,str_OTHours,str_City,
            str_OTAmount,str_TotalAmount, str_StopRider,str_CallCustomer,str_endtime,str_enddate,date,time,
            str_Locality,str_Landmark,endtime,return_date,customer_mobile,booking_id,driverID,CarType;

    Button btn_Close,btn_Submit;

    DatePickerDialog.OnDateSetListener setListener;

    LinearLayout lin2,lin3;

    int year,month,day,hour,minute;

    private static final int REQUEST_PHONE_CALL = 1;;


    @Nullable
    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater,
                             @Nullable  ViewGroup container,
                             @Nullable  Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.bookingdetails,container,false);

        text_BookingType = view.findViewById(R.id.text_BookingType);
        text_CustomerName = view.findViewById(R.id.text_CustomerName);
        text_Address = view.findViewById(R.id.text_Address);
        text_Time = view.findViewById(R.id.text_Time);
        text_Date = view.findViewById(R.id.text_Date);
        text_CarDetails = view.findViewById(R.id.text_CarDetails);
        text_CallCustomer = view.findViewById(R.id.text_CallCustomer);
        text_StopRider = view.findViewById(R.id.text_StopRider);
        text_Shift = view.findViewById(R.id.text_Shift);
        text_Day = view.findViewById(R.id.text_Day);
        text_DutyHours = view.findViewById(R.id.text_DutyHours);
        text_DropLoc = view.findViewById(R.id.text_DropLoc);
        text_Remarks = view.findViewById(R.id.text_Remarks);
        text_Charges = view.findViewById(R.id.text_Charges);
        text_TotalAmount = view.findViewById(R.id.text_TotalAmount);
        textShift = view.findViewById(R.id.textShift);
        textDay = view.findViewById(R.id.textDay);
        textDutyHours = view.findViewById(R.id.textDutyHours);
        text_Locality = view.findViewById(R.id.text_Locality);
        text_Landmark = view.findViewById(R.id.text_Landmark);
        lin2 = view.findViewById(R.id.lin2);
        lin3 = view.findViewById(R.id.lin3);
        //textend_Time = view.findViewById(R.id.textend_Time);
        //text_MobileNo = view.findViewById(R.id.text_MobileNo);

        str_BookingType =  getArguments().getString("bookingType");
        str_CustomerName =  getArguments().getString("customerName");
        str_City =  getArguments().getString("city");
        str_Time =  getArguments().getString("time");
        str_Date =  getArguments().getString("date");
        str_Shift =  getArguments().getString("shift");
        str_Day =  getArguments().getString("day");
        str_DutyHours =  getArguments().getString("DutyHours");
        str_DropLoc =  getArguments().getString("DropLoc");
        str_CarDetails =  getArguments().getString("CarDetails");
        str_Remarks =  getArguments().getString("Remarks");
        str_TotalAmount =  getArguments().getString("TotalAmount");
        str_Address =  getArguments().getString("address");
        str_Charges =  getArguments().getString("Charges");
        str_Locality =  getArguments().getString("Locality");
        str_Landmark =  getArguments().getString("Landmark");
        endtime =  getArguments().getString("endtime");
        return_date =  getArguments().getString("return_date");
        customer_mobile =  getArguments().getString("customer_mobile");
        booking_id =  getArguments().getString("bookingid");
        CarType =  getArguments().getString("CarType");
        driverID = SharedPrefManager_Driver.getInstance(getActivity()).getUser().getDriverID();

        lin2.setVisibility(View.VISIBLE);

        if(str_BookingType.equals("Local")){

            text_BookingType.setText(str_BookingType);
            text_CustomerName.setText(str_CustomerName);
            text_Address.setText(str_Address);
            text_Landmark.setText(str_Landmark);
            text_Locality.setText(str_Locality);
            text_Time.setText(str_Time);
            text_Date.setText(str_Date);

            text_Shift.setText(str_Shift);
            text_Day.setText(str_Day+" "+"Day");
            text_DutyHours.setText(str_DutyHours+" "+"Hours");

            text_DropLoc.setText(str_DropLoc);
            text_Remarks.setText(str_Remarks);
            text_CarDetails.setText(str_CarDetails+"("+CarType+")");

            text_Charges.setText(str_Charges);
            text_TotalAmount.setText(str_TotalAmount);
            //textend_Time.setText(endtime);
            //text_MobileNo.setText(customer_mobile);

        }
        else if(str_BookingType.equals("Outstation")){

            text_BookingType.setText(str_BookingType);
            text_CustomerName.setText(str_CustomerName);
            text_Address.setText(str_Address);
            text_Landmark.setText(str_Landmark);
            text_Locality.setText(str_Locality);
            text_Time.setText(str_Time);
            text_Date.setText(str_Date);
            text_Date.setText(str_Date);
            text_CarDetails.setText(str_CarDetails+"("+CarType+")");

            textShift.setText("Return Date");
            textDay.setText("No of Day");
            textDutyHours.setText("To City");

            text_Shift.setText(str_Shift);
            text_Day.setText(str_Day+" "+"Day");
            text_DutyHours.setText(str_DutyHours);

            text_CarDetails.setText(str_CarDetails);
            text_Remarks.setText(str_Remarks);
            text_Charges.setText(str_Charges);
            //textend_Time.setText(endtime);
            //text_MobileNo.setText(customer_mobile);

            lin2.setVisibility(View.GONE);

        }
        else if(str_BookingType.equals("Drop")){

            text_BookingType.setText(str_BookingType);
            text_CustomerName.setText(str_CustomerName);
            text_Address.setText(str_Address);
            text_Landmark.setText(str_Landmark);
            text_Locality.setText(str_Locality);
            text_Time.setText(str_Time);
            text_Date.setText(str_Date);
            text_Date.setText(str_Date);
            text_CarDetails.setText(str_CarDetails+"("+CarType+")");

            textShift.setVisibility(View.GONE);
            textDay.setVisibility(View.GONE);

            textDutyHours.setText("To City");

            text_Shift.setVisibility(View.GONE);
            text_Day.setVisibility(View.GONE);

            text_DutyHours.setText(str_City);
            //text_MobileNo.setText(customer_mobile);


            text_CarDetails.setText(str_CarDetails);
            text_Remarks.setText(str_Remarks);
            text_Charges.setText(str_Charges);
            //textend_Time.setText(endtime);

        }

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        date = new SimpleDateFormat("dd/mm/yyyy", Locale.getDefault()).format(new Date());
        time = new SimpleDateFormat("HH:mm",Locale.getDefault()).format(new Date());


        //String pattern = "hh:mm aa";
       // SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        //text_StopRider.setVisibility(View.VISIBLE);

        try {

            SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
           // Date date = displayFormat.parse("10:30 PM");
            Date date1 = displayFormat.parse(time);
            Date date2 = displayFormat.parse(endtime);

            if(date1.after(date2)){

                text_StopRider.setVisibility(View.VISIBLE);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        text_CallCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*String number = "tel:"+"9937506028";

                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse(number));
                startActivity(intent);*/

                makePhoneCall();
            }
        });

        text_StopRider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                endBooking(driverID,booking_id);

               /* Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.stopriderdialog);

                btn_Close = dialog.findViewById(R.id.btn_Close);
                btn_Submit = dialog.findViewById(R.id.btn_Submit);
                text_endtime = dialog.findViewById(R.id.text_endtime);
                text_enddate = dialog.findViewById(R.id.text_enddate);

                text_endtime.setText(endtime);
                text_enddate.setText(return_date);

               *//* text_endtime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                int str_hour = hourOfDay;
                                int str_minute = minute;

                                Calendar calendar1 = Calendar.getInstance();

                                String sDate = date;

                                String[] string = sDate.split("/");

                                int sDay = Integer.parseInt(string[0]);

                                calendar1.set(Calendar.DAY_OF_MONTH,sDay);
                                calendar1.set(Calendar.HOUR_OF_DAY,str_hour);
                                calendar1.set(Calendar.MINUTE,str_minute);

                                text_endtime.setText(android.text.format.DateFormat.format("hh:mm aa",calendar1));

                                if(calendar1.getTimeInMillis() == Calendar.getInstance().getTimeInMillis()){

                                    Toast.makeText(getActivity(), "Current Time selected", Toast.LENGTH_SHORT).show();
                                    
                                }else if(calendar1.getTimeInMillis() > Calendar.getInstance().getTimeInMillis()){

                                    Toast.makeText(getActivity(), "Future time selected", Toast.LENGTH_SHORT).show();

                                }else{

                                    Toast.makeText(getActivity(),"Past time selected",Toast.LENGTH_LONG).show();
                                }


                            }
                        },hour,minute,false);


                        timePickerDialog.show();
                    }
                });

                text_enddate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        DatePickerDialog datePickerDialog = new DatePickerDialog(
                                getActivity(), new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                                month = month+1;
                                String date = day+"/"+month+"/"+year;
                                //String date = year+"-"+month+"-"+day;
                                text_enddate.setText(date);
                            }
                        },year,month,day);

                        //display previous selected date
                        datePickerDialog.updateDate(year,month,day);

                        //disiable past date
                        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

                        datePickerDialog.show();
                    }
                });*//*

                btn_Submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

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
                dialog.show();*/
            }
        });

        return view;
    }

    private void makePhoneCall() {
       // String number = items;
      //  String number = "tel:"+customer_mobile;

        if (customer_mobile.trim().length() > 0) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
            } else {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+customer_mobile));
                startActivity(callIntent);
            }
        } else {
            Toast.makeText(getActivity(), "Enter Phone Number", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PHONE_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                makePhoneCall();

            } else {

                Toast.makeText(getActivity(), "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void endBooking(String driverId,String bookingId){

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait....");
        progressDialog.setTitle("Booking End Please Wait....");
        progressDialog.show();

        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put("driver_id",driverId);
            jsonObject.put("booking_id",bookingId);

        }catch(Exception e){

            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppUrl.endBooking, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                progressDialog.dismiss();

                try {
                    String status = response.getString("status");
                    String message = response.getString("message");

                    if(status.equals("true")){

                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

                        text_StopRider.setVisibility(View.INVISIBLE);

                    }else{

                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                Toast.makeText(getActivity(), ""+error, Toast.LENGTH_SHORT).show();
            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30000,1,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.getCache().clear();
        requestQueue.add(jsonObjectRequest);
    }
}
