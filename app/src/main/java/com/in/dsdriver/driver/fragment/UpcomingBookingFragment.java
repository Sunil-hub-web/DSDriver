package com.in.dsdriver.driver.fragment;

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
import com.in.dsdriver.driver.adapter.UpcomingBookingAdapter;
import com.in.dsdriver.extra.AppUrl;
import com.in.dsdriver.extra.SharedPrefManager_Driver;
import com.in.dsdriver.driver.modelclass.UpcomingBooking_ModelClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class UpcomingBookingFragment extends Fragment {

    RecyclerView recyclerUpcomingBooking;
    LinearLayoutManager linearLayoutManager;
    UpcomingBookingAdapter upcomingBookingAdapter;
    ArrayList<UpcomingBooking_ModelClass> upcomingBooking = new ArrayList<>();
    //ArrayList<UpcomingBooking_ModelClass> upcomingBookingTomorrow = new ArrayList<>();

    String driverId,str_date,dateMyFormat;
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

        driverId = SharedPrefManager_Driver.getInstance(getActivity()).getUser().getDriverID();

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

        //DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        //String todayAsString = dateFormat.format(today);
        //String tomorrowAsString = dateFormat.format(tomorrow);

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

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

                //DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

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

                //DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

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

                            String booking_id = jsonObject_allbooking.getString("booking_id");
                            String Locality = jsonObject_allbooking.getString("Locality");
                            String Landmark = jsonObject_allbooking.getString("Landmark");
                            String City = jsonObject_allbooking.getString("City");
                            String bookingType = jsonObject_allbooking.getString("bookingType");
                            String report_date = jsonObject_allbooking.getString("date");
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
                            String charge = jsonObject_allbooking.getString("charge");
                            String customer_name = jsonObject_allbooking.getString("customer_name");
                            String address = jsonObject_allbooking.getString("address");
                            String end_time = jsonObject_allbooking.getString("end_time");

                            String droploc = drop_city +","+ drop_locality;

                            if(date.equals(report_date)){

                                UpcomingBooking_ModelClass upcomingBooking_modelClass = new UpcomingBooking_ModelClass(
                                       bookingType,customer_name,City,report_time,report_date,shift,no_of_day,duty_hour,droploc,
                                        car_details,remark,charge,"0","0",charge,address,return_date,to_city,Locality,Landmark,end_time
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