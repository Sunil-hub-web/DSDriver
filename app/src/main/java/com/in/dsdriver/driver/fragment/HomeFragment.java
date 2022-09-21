package com.in.dsdriver.driver.fragment;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
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
import com.in.dsdriver.extra.SharedPrefManager_Driver;
import com.in.dsdriver.driver.modelclass.TodayReport_ModelClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class HomeFragment extends Fragment {

    Button btn_TodayReportDetails,btn_WorkDetails,btn_MyProfile,btn_CompleteDuty,btn_Wallet;

    TodayReportAdapter todayReportAdapter;
    RecyclerView recyclerReportDetails;
    LinearLayoutManager linearLayoutManager;
    ArrayList<TodayReport_ModelClass> todayReport = new ArrayList<>();

    String driverId;

    TextView text_MyZoen,text_MyCity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable  ViewGroup container,
                             @Nullable  Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home,container,false);

      /*  btn_TodayReportDetails = view.findViewById(R.id.btn_TodayReportDetails);
        btn_WorkDetails = view.findViewById(R.id.btn_WorkDetails);
        btn_MyProfile = view.findViewById(R.id.btn_MyProfile);
        btn_CompleteDuty = view.findViewById(R.id.btn_CompleteDuty);
        btn_Wallet = view.findViewById(R.id.btn_Wallet);*/

       /* btn_WorkDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyReport todayReport = new MyReport();
                FragmentTransaction transaction =  getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment_activity_home_deshbord, todayReport);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        btn_Wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                WalletFragment walletFragment = new WalletFragment();
                FragmentTransaction transaction =  getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment_activity_home_deshbord, walletFragment);
                transaction.addToBackStack(null);
                transaction.commit();

                HomeDeshbord. navView.setSelectedItemId(R.id.navigation_wallet);

            }
        });

        btn_MyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProfileFragment profileFragment = new ProfileFragment();
                FragmentTransaction transaction =  getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment_activity_home_deshbord, profileFragment);
                transaction.addToBackStack(null);
                transaction.commit();

                HomeDeshbord. navView.setSelectedItemId(R.id.navigation_Profile);
            }
        });

        btn_TodayReportDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UpcomingBookingFragment upcomingBookingFragment = new UpcomingBookingFragment();
                FragmentTransaction transaction =  getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment_activity_home_deshbord, upcomingBookingFragment);
                transaction.addToBackStack(null);
                transaction.commit();

                HomeDeshbord. navView.setSelectedItemId(R.id.navigation_upcomingbooking);

            }
        });

        btn_CompleteDuty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BookingHistory bookingHistory = new BookingHistory();
                FragmentTransaction transaction =  getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment_activity_home_deshbord, bookingHistory);
                transaction.addToBackStack(null);
                transaction.commit();

                HomeDeshbord. navView.setSelectedItemId(R.id.navigation_bookingHistory);
            }
        });*/


        recyclerReportDetails = view.findViewById(R.id.recyclerReportDetails);
        text_MyZoen = view.findViewById(R.id.text_MyZoen);
        text_MyCity = view.findViewById(R.id.text_MyCity);

        driverId = SharedPrefManager_Driver.getInstance(getActivity()).getUser().getDriverID();

        myZoneDetails(driverId);

        text_MyCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                todayReport.clear();

                text_MyCity.setBackgroundResource(R.drawable.upcoming_bg);
                text_MyCity.setTextColor(ContextCompat.getColor(getActivity(),R.color.white));

                text_MyZoen.setBackgroundResource(R.drawable.upcoming_bg1);
                text_MyZoen.setTextColor(ContextCompat.getColor(getActivity(),R.color.colorGrey));

                recyclerReportDetails.setVisibility(View.VISIBLE);
                avilableBooking(driverId);

            }
        });

        text_MyZoen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                todayReport.clear();

                text_MyCity.setBackgroundResource(R.drawable.upcoming_bg1);
                text_MyCity.setTextColor(ContextCompat.getColor(getActivity(),R.color.colorGrey1));

                text_MyZoen.setBackgroundResource(R.drawable.upcoming_bg);
                text_MyZoen.setTextColor(ContextCompat.getColor(getActivity(),R.color.white));

                myZoneDetails(driverId);

                recyclerReportDetails.setVisibility(View.VISIBLE);

            }
        });

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
                                String remark = jsonObject_allbooking.getString("remark");
                                String return_date = jsonObject_allbooking.getString("return_date");
                                String drop_city = jsonObject_allbooking.getString("drop_city");
                                String to_city = jsonObject_allbooking.getString("to_city");
                                String driver_type_name = jsonObject_allbooking.getString("driver_type_name");
                                String driver_type = jsonObject_allbooking.getString("driver_type");

                                Calendar calendar = Calendar.getInstance();
                                Date today = calendar.getTime();

                                calendar.add(Calendar.DAY_OF_YEAR, 1);
                                Date tomorrow = calendar.getTime();

                                //DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

                                String todayAsString = dateFormat.format(today);
                                String tomorrowAsString = dateFormat.format(tomorrow);

                                System.out.println(todayAsString);
                                System.out.println(tomorrowAsString);

                                if(date.equals(todayAsString)){


                                    TodayReport_ModelClass todayReport_modelClass = new TodayReport_ModelClass(
                                            booking_id, Locality,Landmark, date, report_time, duty_hour, no_of_day, bookingType,
                                            shift,drop_locality,car_details,remark,return_date,drop_city,to_city,driver_type_name,driver_type
                                    );

                                    todayReport.add(todayReport_modelClass);

                                }else if(date.equals(tomorrowAsString)){

                                    TodayReport_ModelClass todayReport_modelClass = new TodayReport_ModelClass(
                                            booking_id, Locality,Landmark, date, report_time, duty_hour, no_of_day, bookingType,
                                            shift,drop_locality,car_details,remark,return_date,drop_city,to_city,driver_type_name,driver_type
                                    );

                                    todayReport.add(todayReport_modelClass);

                                }

                                String address = Locality + "," + Landmark;

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

    public void myZoneDetails(String driverId) {

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Show MyZone Please Wait....");
        progressDialog.show();

        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put("driver_id", driverId);

        } catch (Exception e) {

            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppUrl.availableBooking_zoewise, jsonObject, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
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
                                String driver_type_name = jsonObject_allbooking.getString("driver_type_name");
                                String driver_type = jsonObject_allbooking.getString("driver_type");


                                Calendar calendar = Calendar.getInstance();
                                Date today = calendar.getTime();

                                calendar.add(Calendar.DAY_OF_YEAR, 1);
                                Date tomorrow = calendar.getTime();

                                //DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

                                String todayAsString = dateFormat.format(today);
                                String tomorrowAsString = dateFormat.format(tomorrow);

                                System.out.println(todayAsString);
                                System.out.println(tomorrowAsString);

                                if(date.equals(todayAsString)){

                                    TodayReport_ModelClass todayReport_modelClass = new TodayReport_ModelClass(
                                            booking_id, Locality,Landmark, date, report_time, duty_hour, no_of_day, bookingType,
                                            shift,drop_locality,car_details,remark,return_date,drop_city,to_city,driver_type_name,driver_type
                                    );


                                    todayReport.add(todayReport_modelClass);

                                }else if(date.equals(tomorrowAsString)){

                                    TodayReport_ModelClass todayReport_modelClass = new TodayReport_ModelClass(
                                            booking_id, Locality,Landmark, date, report_time, duty_hour, no_of_day, bookingType,
                                            shift,drop_locality,car_details,remark,return_date,drop_city,to_city,driver_type_name,driver_type
                                    );


                                    todayReport.add(todayReport_modelClass);

                                }

                                String address = Locality + "," + Landmark;
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