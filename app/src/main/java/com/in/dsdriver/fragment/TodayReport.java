package com.in.dsdriver.fragment;

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
import com.in.dsdriver.adapter.TodayReportAdapter;
import com.in.dsdriver.extra.AppUrl;
import com.in.dsdriver.extra.SharedPrefManager;
import com.in.dsdriver.modelclass.TodayReport_ModelClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class TodayReport extends Fragment {

    TodayReportAdapter todayReportAdapter;
    RecyclerView recyclerReportDetails;
    LinearLayoutManager linearLayoutManager;
    ArrayList<TodayReport_ModelClass> todayReport = new ArrayList<>();

    String driverId;

    @Nullable
    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater,
                             @Nullable  ViewGroup container, @Nullable  Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_todayreportdetails,container,false);

        recyclerReportDetails = view.findViewById(R.id.recyclerReportDetails);

        driverId = SharedPrefManager.getInstance(getActivity()).getUser().getDriverID();

        avilableBooking(driverId);
/*
        todayReport.add(new TodayReport_ModelClass("Andheri, Mumbai",
                "12/01/2022","2:35 PM","8hours","2 Days"));*/


        return view;
    }

    public void avilableBooking(String driverId){

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Show Booking Details Please Wait....");
        progressDialog.show();

        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put("driver_id",driverId);

            }catch(Exception e){

            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppUrl.availableBooking, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                progressDialog.dismiss();

                try {
                    String status = response.getString("status");

                    if(status.equals("success")){

                        String allbooking = response.getString("allbooking");

                        JSONArray jsonArray_allbooking = new JSONArray(allbooking);

                        for(int i = 0;i<jsonArray_allbooking.length();i++){

                            JSONObject jsonObject_allbooking = jsonArray_allbooking.getJSONObject(i);

                            String id = jsonObject_allbooking.getString("id");
                            String customer_id = jsonObject_allbooking.getString("customer_id");
                            String address_id = jsonObject_allbooking.getString("address_id");
                            String driver_id = jsonObject_allbooking.getString("driver_id");
                            String invoice_for = jsonObject_allbooking.getString("invoice_for");
                            String reporting_time = jsonObject_allbooking.getString("reporting_time");
                            String form_date = jsonObject_allbooking.getString("form_date");
                            String reporting_date = jsonObject_allbooking.getString("reporting_date");
                            String reporting_end_date = jsonObject_allbooking.getString("reporting_end_date");
                            String driver_type = jsonObject_allbooking.getString("driver_type");
                            String car_detail = jsonObject_allbooking.getString("car_detail");
                            String duty_type = jsonObject_allbooking.getString("duty_type");
                            String duty_hours = jsonObject_allbooking.getString("duty_hours");
                            String to_city = jsonObject_allbooking.getString("to_city");
                            String end_city = jsonObject_allbooking.getString("end_city");
                            String datecount = jsonObject_allbooking.getString("datecount");
                            String rate = jsonObject_allbooking.getString("rate");
                            String commision = jsonObject_allbooking.getString("commision");
                            String reference = jsonObject_allbooking.getString("reference");
                            String status1 = jsonObject_allbooking.getString("status");

                            String address = to_city +","+ end_city;

                            TodayReport_ModelClass todayReport_modelClass = new TodayReport_ModelClass(
                                    id,address,reporting_date,reporting_time,duty_hours,datecount,duty_type
                            );

                            todayReport.add(todayReport_modelClass);

                        }

                        linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
                        todayReportAdapter = new TodayReportAdapter(getContext(),todayReport);
                        recyclerReportDetails.setLayoutManager(linearLayoutManager);
                        recyclerReportDetails.setHasFixedSize(true);
                        recyclerReportDetails.setAdapter(todayReportAdapter);

                    }else if(status.equals("false")){

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
                Toast.makeText(getActivity(), ""+error, Toast.LENGTH_SHORT).show();

            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30000,1,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.getCache().clear();
        requestQueue.add(jsonObjectRequest);
    }
}
