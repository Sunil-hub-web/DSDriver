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
import com.in.dsdriver.extra.SharedPrefManager_Driver;
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

        str_driverId = SharedPrefManager_Driver.getInstance(getActivity()).getUser().getDriverID();

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

                            String booking_id = jsonObject_allbooking.getString("booking_id");

                            String Locality = jsonObject_allbooking.getString("Locality");
                            String City = jsonObject_allbooking.getString("City");
                            String Landmark = jsonObject_allbooking.getString("Landmark");
                            String bookingType = jsonObject_allbooking.getString("bookingType");
                            String date = jsonObject_allbooking.getString("date");
                            String report_time = jsonObject_allbooking.getString("report_time");
                            String shift = jsonObject_allbooking.getString("shift");
                            String no_of_day = jsonObject_allbooking.getString("no_of_day");
                            String duty_hour = jsonObject_allbooking.getString("duty_hour");
                            String drop_locality = jsonObject_allbooking.getString("drop_locality");
                            String car_details = jsonObject_allbooking.getString("car_details");
                            String return_date = jsonObject_allbooking.getString("return_date");
                            String drop_city = jsonObject_allbooking.getString("drop_city");
                            String to_city = jsonObject_allbooking.getString("to_city");
                            String charge = jsonObject_allbooking.getString("charge");
                            String customer_name = jsonObject_allbooking.getString("customer_name");
                            String address = jsonObject_allbooking.getString("address");
                            String end_time = jsonObject_allbooking.getString("end_time");
                            String dutyType = jsonObject_allbooking.getString("dutyType");


                            BookingHistory_ModelClass bookingHistory_modelClass = new BookingHistory_ModelClass(
                                   booking_id,return_date,report_time,City,Locality,"",dutyType,charge
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
