package com.in.dsdriver.driver.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.in.dsdriver.R;
import com.in.dsdriver.driver.adapter.TodayReportAdapter;
import com.in.dsdriver.extra.AppUrl;
import com.in.dsdriver.extra.SharedPrefManager;
import com.in.dsdriver.driver.modelclass.TodayReport_ModelClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MyReport extends Fragment {

    TodayReportAdapter todayReportAdapter;
    RecyclerView recyclerReportDetails;
    LinearLayoutManager linearLayoutManager;
    ArrayList<TodayReport_ModelClass> todayReport = new ArrayList<>();

    String driverId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_todayreportdetails, container, false);

        recyclerReportDetails = view.findViewById(R.id.recyclerReportDetails);

        driverId = SharedPrefManager.getInstance(getActivity()).getUser().getDriverID();

        avilableBooking(driverId);
/*
        todayReport.add(new TodayReport_ModelClass("Andheri, Mumbai",
                "12/01/2022","2:35 PM","8hours","2 Days"));*/


        return view;
    }

    public void avilableBooking(String driverId) {

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Show Booking Details Please Wait....");
        progressDialog.show();

        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put("driver_id", driverId);

        } catch (Exception e) {

            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppUrl.availableBooking, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                progressDialog.dismiss();

                try {
                    String status = response.getString("status");

                    if (status.equals("success")) {

                        String allbooking = response.getString("allbooking");

                        JSONArray jsonArray_allbooking = new JSONArray(allbooking);

                        if (jsonArray_allbooking.length() == 0) {

                            Toast.makeText(getActivity(), "No Data Found", Toast.LENGTH_SHORT).show();

                        } else {

                            for (int i = 0; i < jsonArray_allbooking.length(); i++) {

                                JSONObject jsonObject_allbooking = jsonArray_allbooking.getJSONObject(i);

                                String booking_id = jsonObject_allbooking.getString("booking_id");
                                String Locality = jsonObject_allbooking.getString("Locality");
                                String Landmark = jsonObject_allbooking.getString("Landmark");
                                String bookingType = jsonObject_allbooking.getString("bookingType");
                                String date = jsonObject_allbooking.getString("date");
                                String report_time = jsonObject_allbooking.getString("report_time");
                                String shift = jsonObject_allbooking.getString("shift");
                                String no_of_day = jsonObject_allbooking.getString("no_of_day");
                                String duty_hour = jsonObject_allbooking.getString("duty_hour");
                                String drop_locality = jsonObject_allbooking.getString("drop_locality");
                                String car_details = jsonObject_allbooking.getString("car_details");
                                String remark = jsonObject_allbooking.getString("remark");
                                String return_date = jsonObject_allbooking.getString("return_date");
                                String drop_city = jsonObject_allbooking.getString("drop_city");
                                String to_city = jsonObject_allbooking.getString("to_city");

                                String address = Locality + "," + Landmark;

                                TodayReport_ModelClass todayReport_modelClass = new TodayReport_ModelClass(
                                        booking_id, Locality,Landmark, date, report_time, duty_hour, no_of_day, bookingType,
                                        shift,drop_locality,car_details,remark,return_date,drop_city,to_city
                                );


                                todayReport.add(todayReport_modelClass);

                            }

                            linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                            todayReportAdapter = new TodayReportAdapter(getContext(), todayReport);
                            recyclerReportDetails.setLayoutManager(linearLayoutManager);
                            recyclerReportDetails.setHasFixedSize(true);
                            recyclerReportDetails.setAdapter(todayReportAdapter);
                        }

                    } else if (status.equals("false")) {

                        String message = response.getString("message");
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
                error.printStackTrace();
                Toast.makeText(getActivity(), "" + error, Toast.LENGTH_SHORT).show();

            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.getCache().clear();
        requestQueue.add(jsonObjectRequest);
    }
}
