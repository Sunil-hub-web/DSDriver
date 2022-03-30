package com.in.dsdriver.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.in.dsdriver.adapter.BookingHistoryAdapter;
import com.in.dsdriver.adapter.UpcomingBookingAdapter;
import com.in.dsdriver.extra.AppUrl;
import com.in.dsdriver.extra.SharedPrefManager;
import com.in.dsdriver.modelclass.BookingHistory_ModelClass;
import com.in.dsdriver.modelclass.UpcomingBooking_ModelClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class UpcomingBookingFragment extends Fragment {

    RecyclerView recyclerUpcomingBooking;
    LinearLayoutManager linearLayoutManager;
    UpcomingBookingAdapter upcomingBookingAdapter;
    ArrayList<UpcomingBooking_ModelClass> upcomingBooking = new ArrayList<>();
    //ArrayList<UpcomingBooking_ModelClass> upcomingBookingTomorrow = new ArrayList<>();

    String driverId,str_date;
    int date;

    TextView text_TodayDuties,text_TomorrowDuties;

    @Nullable
    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater,
                             @Nullable  ViewGroup container,
                             @Nullable  Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_upcomingbooking,container,false);

        recyclerUpcomingBooking = view.findViewById(R.id.recyclerUpcomingBooking);
        text_TomorrowDuties = view.findViewById(R.id.text_TomorrowDuties);
        text_TodayDuties = view.findViewById(R.id.text_TodayDuties);

        driverId = SharedPrefManager.getInstance(getActivity()).getUser().getDriverID();

        /*GregorianCalendar gc = new GregorianCalendar();
       // yearat = gc.get(Calendar.YEAR);
        //yearstr = Integer.toString(yearat);
        //monthat = gc.get(Calendar.MONTH) + 1;
        //monthstr = Integer.toString(monthat);
        date = gc.get(Calendar.DAY_OF_YEAR);
        str_date = Integer.toString(date);

        Log.d("todayyear",str_date);


        //date = gc.get(Calendar.DAY_OF_MONTH) + 1;

        assignBooking(driverId,str_date);*/

        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        //String todayAsString = dateFormat.format(today);
        //String tomorrowAsString = dateFormat.format(tomorrow);

        str_date = dateFormat.format(today);

        assignBooking(driverId,str_date);


        text_TodayDuties.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                upcomingBooking.clear();

                text_TodayDuties.setBackgroundResource(R.drawable.upcoming_bg);
                text_TodayDuties.setTextColor(ContextCompat.getColor(getActivity(),R.color.white));

                text_TomorrowDuties.setBackgroundResource(R.drawable.upcoming_bg1);
                text_TomorrowDuties.setTextColor(ContextCompat.getColor(getActivity(),R.color.colorGrey));

                    //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    //String currentDateandTime = sdf.format(new Date());


                Calendar calendar = Calendar.getInstance();
                Date today = calendar.getTime();

                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                //String todayAsString = dateFormat.format(today);
                //String tomorrowAsString = dateFormat.format(tomorrow);

                str_date = dateFormat.format(today);

                assignBooking(driverId,str_date);


            }
        });

        text_TomorrowDuties.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                upcomingBooking.clear();

                text_TodayDuties.setBackgroundResource(R.drawable.upcoming_bg1);
                text_TodayDuties.setTextColor(ContextCompat.getColor(getActivity(),R.color.colorGrey1));

                text_TomorrowDuties.setBackgroundResource(R.drawable.upcoming_bg);
                text_TomorrowDuties.setTextColor(ContextCompat.getColor(getActivity(),R.color.white));

                Calendar calendar = Calendar.getInstance();
                //Date today = calendar.getTime();

                calendar.add(Calendar.DAY_OF_YEAR, 1);
                Date tomorrow = calendar.getTime();

                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                //String todayAsString = dateFormat.format(today);
                //String tomorrowAsString = dateFormat.format(tomorrow);

                str_date = dateFormat.format(tomorrow);

                assignBooking(driverId,str_date);

            }
        });

        return view;
    }

    public void assignBooking(String driverId,String date){

        upcomingBooking.clear();

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Show Booking Details Please Wait....");
        progressDialog.show();

        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put("driver_id",driverId);

        }catch(Exception e){

            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppUrl.allAssignedBooking, jsonObject, new Response.Listener<JSONObject>() {
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

                            if(date.equals(reporting_date)){

                                UpcomingBooking_ModelClass upcomingBooking_modelClass = new UpcomingBooking_ModelClass(
                                        reporting_date,reporting_time,to_city,end_city,duty_type,duty_hours,car_detail,customer_id,address
                                );

                                upcomingBooking.add(upcomingBooking_modelClass);

                                Log.d("upcomingBooking",upcomingBooking.toString());

                            }
                        }

                        if(upcomingBooking.size() != 0){

                            recyclerUpcomingBooking.setVisibility(View.VISIBLE);

                            linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
                      /*      linearLayoutManager.setReverseLayout(true);
                            linearLayoutManager.setStackFromEnd(true);*/
                            upcomingBookingAdapter = new UpcomingBookingAdapter(getContext(),upcomingBooking);
                            recyclerUpcomingBooking.setLayoutManager(linearLayoutManager);
                            recyclerUpcomingBooking.setHasFixedSize(true);
                            recyclerUpcomingBooking.setAdapter(upcomingBookingAdapter);

                            Log.d("upcomingBooking1",upcomingBooking.toString());

                        }else{

                            upcomingBooking.clear();
                            recyclerUpcomingBooking.setVisibility(View.INVISIBLE);
                            Toast.makeText(getActivity(), "No Data Found", Toast.LENGTH_SHORT).show();


                        }

                    } else if(status.equals("false")){

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