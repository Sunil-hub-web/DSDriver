package com.in.dsdriver.fragment;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
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

import com.in.dsdriver.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static java.text.DateFormat.*;

public class BookingDetails extends Fragment {

    TextView text_DutyType,text_CustomerName,text_Address,text_Time,text_CareModel,text_CallCustomer,
            text_StopRider,text_endtime,text_enddate;

    String str_DutyType,str_CustomerName,str_Address,str_Time,str_CareModel,date,time;

    Button btn_Close,btn_Submit;

    DatePickerDialog.OnDateSetListener setListener;

    int year,month,day,hour,minute;

    private static final int REQUEST_PHONE_CALL = 1;;


    @Nullable
    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater,
                             @Nullable  ViewGroup container,
                             @Nullable  Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.bookingdetails,container,false);

        text_DutyType = view.findViewById(R.id.text_DutyType);
        text_CustomerName = view.findViewById(R.id.text_CustomerName);
        text_Address = view.findViewById(R.id.text_Address);
        text_Time = view.findViewById(R.id.text_Time);
        text_CareModel = view.findViewById(R.id.text_CareModel);
        text_CallCustomer = view.findViewById(R.id.text_CallCustomer);
        text_StopRider = view.findViewById(R.id.text_StopRider);

        str_DutyType =  getArguments().getString("param1");
        str_Address =  getArguments().getString("param2");
        str_Time =  getArguments().getString("param3");
        str_CareModel =  getArguments().getString("param4");
        str_CustomerName =  getArguments().getString("param5");
        //str_DutyType =  getArguments().getString("param5");

        text_DutyType.setText(str_DutyType);
        text_Address.setText(str_Address);
        text_Time.setText(str_Time);
        text_CareModel.setText(str_CareModel);
        text_CustomerName.setText(str_CustomerName);

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        date = new SimpleDateFormat("dd/mm/yyyy", Locale.getDefault()).format(new Date());
        time = new SimpleDateFormat("hh:mm aa",Locale.getDefault()).format(new Date());

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

                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.stopriderdialog);

                btn_Close = dialog.findViewById(R.id.btn_Close);
                btn_Submit = dialog.findViewById(R.id.btn_Submit);
                text_endtime = dialog.findViewById(R.id.text_endtime);
                text_enddate = dialog.findViewById(R.id.text_enddate);

                text_endtime.setOnClickListener(new View.OnClickListener() {
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

                               /* if(calendar1.getTimeInMillis() == Calendar.getInstance().getTimeInMillis()){

                                    Toast.makeText(getActivity(), "Current Time selected", Toast.LENGTH_SHORT).show();
                                    
                                }else if(calendar1.getTimeInMillis() > Calendar.getInstance().getTimeInMillis()){

                                    Toast.makeText(getActivity(), "Future time selected", Toast.LENGTH_SHORT).show();

                                }else{

                                    Toast.makeText(getActivity(),"Past time selected",Toast.LENGTH_LONG).show();
                                }
*/

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
                });

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
                dialog.show();
            }
        });

        return view;
    }

    private void makePhoneCall() {
       // String number = items;
        String number = "tel:"+"9937506028";
        if (number.trim().length() > 0) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
            } else {
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
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
}
