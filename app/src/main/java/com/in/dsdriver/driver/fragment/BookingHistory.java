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
import com.in.dsdriver.driver.adapter.BookingHistoryAdapter;
import com.in.dsdriver.extra.AppUrl;
import com.in.dsdriver.extra.SharedPrefManager;
import com.in.dsdriver.driver.modelclass.BookingHistory_ModelClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BookingHistory extends Fragment {

    RecyclerView recyclerBookingHistory;
    LinearLayoutManager linearLayoutManager;
    BookingHistoryAdapter upcomingBookingAdapter;
    ArrayList<BookingHistory_ModelClass> bookingHistory = new ArrayList<>();

    String str_driverId;

    @Nullable
    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater,
                             @Nullable  ViewGroup container,
                             @Nullable  Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bookinghistory,container,false);

        recyclerBookingHistory = view.findViewById(R.id.recyclerBookingHistory);

        str_driverId = SharedPrefManager.getInstance(getActivity()).getUser().getDriverID();

        assignBooking(str_driverId);

        return view;
    }

    public void assignBooking(String driverId){

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait....");
        progressDialog.setTitle("Show Booking Details");
        progressDialog.show();

        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put("driver_id",driverId);

        }catch(Exception e){

            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppUrl.allCompletedBooking, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                progressDialog.dismiss();

                try {

                    String status = response.getString("status");

                    if(status.equals("success")){

                        String assignBooking = response.getString("assignBooking");

                        JSONArray jsonArray_assignBooking = new JSONArray(assignBooking);

                        for(int i = 0;i<jsonArray_assignBooking.length();i++){

                            JSONObject jsonObject_allbooking = jsonArray_assignBooking.getJSONObject(i);

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

                            BookingHistory_ModelClass bookingHistory_modelClass = new BookingHistory_ModelClass(
                                    id,reporting_date,reporting_time,to_city,end_city,datecount,duty_type,rate
                            );


                            bookingHistory.add(bookingHistory_modelClass);

                        }

                        linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
                        upcomingBookingAdapter = new BookingHistoryAdapter(getContext(),bookingHistory);
                        recyclerBookingHistory.setLayoutManager(linearLayoutManager);
                        recyclerBookingHistory.setHasFixedSize(true);
                        recyclerBookingHistory.setAdapter(upcomingBookingAdapter);

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
