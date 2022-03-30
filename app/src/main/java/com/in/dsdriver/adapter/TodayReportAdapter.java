package com.in.dsdriver.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.in.dsdriver.R;
import com.in.dsdriver.extra.AppUrl;
import com.in.dsdriver.extra.SharedPrefManager;
import com.in.dsdriver.modelclass.TodayReport_ModelClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TodayReportAdapter extends RecyclerView.Adapter<TodayReportAdapter.ViewHolder> {

    Context context;
    ArrayList<TodayReport_ModelClass> reporttoday;
    String driverId;

    public TodayReportAdapter(Context context, ArrayList<TodayReport_ModelClass> todayReport) {

        this.context = context;
        this.reporttoday = todayReport;
    }

    @NonNull
    @Override
    public TodayReportAdapter.ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todayreportdetails,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  TodayReportAdapter.ViewHolder holder, int position) {

        TodayReport_ModelClass today_Report = reporttoday.get(position);

        driverId = SharedPrefManager.getInstance(context).getUser().getDriverID();

        holder.text_Address.setText(today_Report.getAddress());
        holder.text_date.setText(today_Report.getDate());
        holder.text_Time.setText(today_Report.getTime());

        if(today_Report.getDuty_type().equals("Local")){

            holder.text_NoofDays.setVisibility(View.GONE);
            holder.text4.setVisibility(View.GONE);
            //holder.text_DutyType.setText(today_Report.getDutyHour());
            holder.text_DutyType.setText(today_Report.getDuty_type());

        }else if(today_Report.getDuty_type().equals("Out Station")){

            holder.text_NoofDays.setVisibility(View.VISIBLE);
            holder.text4.setVisibility(View.VISIBLE);

            //holder.text_DutyType.setText(today_Report.getDutyHour()+" "+"hours");
            holder.text_NoofDays.setText(today_Report.getNoofDays()+" "+"Days");

            holder.text_DutyType.setText(today_Report.getDuty_type());
            holder.text_NoofDays.setText(today_Report.getNoofDays()+" "+"Days");

        }else if(today_Report.getDuty_type().equals("0")){

            holder.text_DutyType.setText(today_Report.getDuty_type());
            holder.text_NoofDays.setText(today_Report.getNoofDays()+" "+"Days");

        }else{

            holder.text_DutyType.setText(today_Report.getDuty_type());
            holder.text_NoofDays.setText(today_Report.getNoofDays()+" "+"Days");
        }

        holder.text_acceptBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String bookingId = today_Report.getId();

                acceptBooking(driverId,bookingId);

                reporttoday.remove(position);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return reporttoday.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView text_Address,text_date,text_Time,text_DutyType,text_NoofDays,text4,text_acceptBooking;

        public ViewHolder(@NonNull  View itemView) {
            super(itemView);

            text_DutyType = itemView.findViewById(R.id.text_DutyType);
            text_Address = itemView.findViewById(R.id.text_Address);
            text_date = itemView.findViewById(R.id.text_date);
            text_Time = itemView.findViewById(R.id.text_Time);
            text_NoofDays = itemView.findViewById(R.id.text_NoofDays);
            text4 = itemView.findViewById(R.id.text4);
            text_acceptBooking = itemView.findViewById(R.id.text_acceptBooking);
        }
    }

    public void acceptBooking(String driverId,String bookingId){

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Accept Booking Please Wait....");
        progressDialog.show();

        JSONObject jsonObject = new JSONObject();

        try{

            jsonObject.put("driver_id",driverId);
            jsonObject.put("booking_id",bookingId);

        }catch(Exception e){

            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppUrl.acceptBooking, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                progressDialog.dismiss();

                try {
                    String status = response.getString("status");

                    if(status.equals("success")){
                        
                        String message = response.getString("message");
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

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
                Toast.makeText(context, ""+error, Toast.LENGTH_SHORT).show();
            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30000,1,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.getCache().clear();
        requestQueue.add(jsonObjectRequest);
    }
}
