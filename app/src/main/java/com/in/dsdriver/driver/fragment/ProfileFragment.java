package com.in.dsdriver.driver.fragment;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.in.dsdriver.LoginPage;
import com.in.dsdriver.R;
import com.in.dsdriver.extra.AppUrl;
import com.in.dsdriver.extra.SharedPrefManager_Driver;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {

    EditText text_driverName,text_driverContactNo,text_driverEmailId,text_driverDob,text_driverAddress,text_DrivingLicence;

    TextView text_logout,text_edit;

    String str_driverName,str_driverContactNo,str_driverEmailId,str_driverDob,str_driverAddress,
            str_DrivingLicence,str_DariverId;

    DatePickerDialog.OnDateSetListener setListener;

    int year,month,day;

    @Nullable
    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater, @Nullable  ViewGroup container,
                             @Nullable  Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile,container,false);

        text_logout = view.findViewById(R.id.text_logout);
        text_DrivingLicence = view.findViewById(R.id.text_DrivingLicence);
        text_driverName = view.findViewById(R.id.text_driverName);
        text_driverContactNo = view.findViewById(R.id.text_driverContactNo);
        text_driverEmailId = view.findViewById(R.id.text_driverEmailId);
        text_driverDob = view.findViewById(R.id.text_driverDob);
        text_driverAddress = view.findViewById(R.id.text_driverAddress);
        text_edit = view.findViewById(R.id.text_edit);

        str_DariverId = SharedPrefManager_Driver.getInstance(getActivity()).getUser().getDriverID();

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                month = month+1;
                String date = day+"/"+month+"/"+year;
                //String date = year+"-"+month+"-"+day;
                text_driverDob.setText(date);

            }
        };

        text_driverDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showCalander();
            }
        });

        text_logout.setOnClickListener(v -> {
            //Show Your Another AlertDialog
            final Dialog dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.condition_logout);
            dialog.setCancelable(false);
            Button btn_No = dialog.findViewById(R.id.no);
            TextView textView = dialog.findViewById(R.id.editText);
            Button btn_Yes = dialog.findViewById(R.id.yes);

            btn_Yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //SharedPrefManager_Owner.getInstance(MainActivity.this).logout();

                    dialog.dismiss();
                   /* System.exit(1);
                    finish();*/

                    SharedPrefManager_Driver.getInstance(getActivity()).logout();

                    Intent intent = new Intent(getActivity(), LoginPage.class);
                    startActivity(intent);

                }
            });
            btn_No.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog.dismiss();
                }
            });

            dialog.show();
            Window window = dialog.getWindow();
            window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawableResource(R.drawable.edittextback);

        });

        getDriverDetails(str_DariverId);

        text_edit.setOnClickListener(v -> {

            if (text_edit.getText().toString().trim().equals("Edit")){

                text_driverName.setEnabled(true);
                text_driverEmailId.setEnabled(true);
                text_driverDob.setEnabled(true);
                text_driverAddress.setEnabled(true);
                text_DrivingLicence.setEnabled(true);
                //text_driverContactNo.setEnabled(false);

                text_edit.setText("Save");

            }else if(text_edit.getText().toString().trim().equals("Save")){

                if(TextUtils.isEmpty(text_driverName.getText())){

                    text_driverName.setError("Fill The Details");

                }else if(TextUtils.isEmpty(text_driverEmailId.getText())){

                    text_driverEmailId.setError("Fill The Details");

                }else if(TextUtils.isEmpty(text_driverDob.getText())){

                    text_driverDob.setError("Fill The Details");

                }else if(TextUtils.isEmpty(text_driverAddress.getText())){

                    text_driverAddress.setError("Fill The Details");

                }else if(TextUtils.isEmpty(text_DrivingLicence.getText())){

                    text_DrivingLicence.setError("Fill The Details");

                }else{

                    str_driverName = text_driverName.getText().toString().trim();
                    str_driverEmailId = text_driverEmailId.getText().toString().trim();
                    str_driverDob = text_driverDob.getText().toString().trim();
                    str_driverAddress = text_driverAddress.getText().toString().trim();
                    str_DrivingLicence = text_DrivingLicence.getText().toString().trim();

                    updateDetails(str_DariverId,str_driverName,str_driverEmailId,str_driverDob,str_DrivingLicence,str_driverAddress);


                }

            }

        });

        return view;
    }

    public void getDriverDetails(String driverId){

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please Wait....");
        progressDialog.setTitle("Get ProfileDetails");
        progressDialog.show();

        Map<String,String> params = new HashMap<>();

        try{

            params.put("driver_id",driverId);

        }catch(Exception e){

            e.printStackTrace();
        }

        JSONObject jsonObject = new JSONObject(params);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppUrl.getDriverProfile, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                progressDialog.dismiss();

                try {
                    String status = response.getString("status");

                    if(status.equals("true")){

                        String driver_id = response.getString("driver_id");
                        String name = response.getString("name");
                        String email = response.getString("email");
                        String mobile = response.getString("mobile");
                        String dob = response.getString("dob");
                        String dl_no = response.getString("dl_no");
                        String adresss = response.getString("adresss");


                        SimpleDateFormat fromUser = new SimpleDateFormat("yyyy/MM/dd");
                        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");

                        try {

                            Date dateFromUser = fromUser.parse(dob); // Parse it to the exisitng date pattern and return Date type
                            String dateMyFormat = myFormat.format(dateFromUser); // format it to the date pattern you prefer
                            text_driverDob.setText(dateMyFormat);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        text_driverName.setText(name);
                        text_driverEmailId.setText(email);

                        text_driverAddress.setText(adresss);
                        text_DrivingLicence.setText(dl_no);
                        text_driverContactNo.setText(mobile);

                        text_driverName.setEnabled(false);
                        text_driverEmailId.setEnabled(false);
                        text_driverDob.setEnabled(false);
                        text_driverAddress.setEnabled(false);
                        text_DrivingLicence.setEnabled(false);
                        text_driverContactNo.setEnabled(false);


                    }else if(status.equalsIgnoreCase("false")){

                        String message = response.getString("message");
                        Toast.makeText(getActivity(), "message", Toast.LENGTH_SHORT).show();

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
                Toast.makeText(getActivity(), ""+error, Toast.LENGTH_SHORT).show();
            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30000,1,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonObjectRequest);


    }

    public void updateDetails(String driver_id,String driverName,String driverEmail,String driverDOB,
                              String driverDlNo,String driverAddress){

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please Wait....");
        progressDialog.setTitle("Update ProfileDetails");
        progressDialog.show();

        Map<String,String> params = new HashMap<>();

        try{

            params.put("driver_id",driver_id);
            params.put("name",driverName);
            params.put("email",driverEmail);
            params.put("dob",driverDOB);
            params.put("licence_no",driverDlNo);
            params.put("address",driverAddress);

        }catch (Exception e){

            e.printStackTrace();
        }

        JSONObject jsonObject = new JSONObject(params);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppUrl.updateDriverProfile, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                progressDialog.dismiss();

                try {
                    String status = response.getString("status");

                    if (status.equals("true")){

                        String message = response.getString("message");
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

                        String driver_id = response.getString("driver_id");
                        String name = response.getString("name");
                        String email = response.getString("email");
                        String mobile = response.getString("mobile");
                        String dob = response.getString("dob");
                        String dl_no = response.getString("dl_no");
                        String adresss = response.getString("adresss");

                        SimpleDateFormat fromUser = new SimpleDateFormat("yyyy/MM/dd");
                        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");

                        try {

                            Date dateFromUser = fromUser.parse(dob); // Parse it to the exisitng date pattern and return Date type
                            String dateMyFormat = myFormat.format(dateFromUser); // format it to the date pattern you prefer
                            text_driverDob.setText(dateMyFormat);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        text_driverName.setText(name);
                        text_driverEmailId.setText(email);
                        //text_driverDob.setText(dob);
                        text_driverAddress.setText(adresss);
                        text_DrivingLicence.setText(dl_no);
                        text_driverContactNo.setText(mobile);

                        text_driverName.setEnabled(false);
                        text_driverEmailId.setEnabled(false);
                        text_driverDob.setEnabled(false);
                        text_driverAddress.setEnabled(false);
                        text_DrivingLicence.setEnabled(false);
                        text_driverContactNo.setEnabled(false);

                        text_edit.setText("Edit");

                    }else if(status.equalsIgnoreCase("false")){

                        String message = response.getString("message");
                        Toast.makeText(getActivity(), "message", Toast.LENGTH_SHORT).show();

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
                Toast.makeText(getActivity(), ""+error, Toast.LENGTH_SHORT).show();

            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30000,1,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonObjectRequest);
    }


    public void showCalander(){

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getActivity(), android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                setListener,year,month,day);

        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.show();

    }

    public void showCalender1(){

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                month = month+1;
                String date = day+"/"+month+"/"+year;
                //String date = year+"-"+month+"-"+day;
                text_driverDob.setText(date);
            }
        },year,month,day);

        datePickerDialog.show();
    }

}