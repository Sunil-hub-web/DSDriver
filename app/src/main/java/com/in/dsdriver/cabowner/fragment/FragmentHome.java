package com.in.dsdriver.cabowner.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
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

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.in.dsdriver.R;
import com.in.dsdriver.cabowner.SharedPrefManager_Owner;
import com.in.dsdriver.cabowner.adapter.CabMyZoneAdapter;
import com.in.dsdriver.cabowner.modelclass.CabMyZone_ModelClass;
import com.in.dsdriver.extra.AppUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FragmentHome extends Fragment {

    RecyclerView recyclerReportDetails;
    LinearLayoutManager linearLayoutManager;
    CabMyZoneAdapter cabMyZoneAdapter;
    ArrayList<CabMyZone_ModelClass> cabMyZone;
    TextView text_MyZoen,text_MyCity;
    String cabOwnerId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_fragment,container,false);

        recyclerReportDetails = view.findViewById(R.id.recyclerReportDetails);
        text_MyCity = view.findViewById(R.id.text_MyCity);
        text_MyZoen = view.findViewById(R.id.text_MyZoen);

        recyclerReportDetails.setVisibility(View.VISIBLE);

        cabOwnerId = SharedPrefManager_Owner.getInstance(getActivity()).getUser().getCabOwnerId();
        getMyZone(cabOwnerId);

        text_MyCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                text_MyCity.setBackgroundResource(R.drawable.upcoming_bg);
                text_MyCity.setTextColor(ContextCompat.getColor(getActivity(),R.color.white));

                text_MyZoen.setBackgroundResource(R.drawable.upcoming_bg1);
                text_MyZoen.setTextColor(ContextCompat.getColor(getActivity(),R.color.colorGrey));

                recyclerReportDetails.setVisibility(View.GONE);
            }
        });

        text_MyZoen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                text_MyCity.setBackgroundResource(R.drawable.upcoming_bg1);
                text_MyCity.setTextColor(ContextCompat.getColor(getActivity(),R.color.colorGrey1));

                text_MyZoen.setBackgroundResource(R.drawable.upcoming_bg);
                text_MyZoen.setTextColor(ContextCompat.getColor(getActivity(),R.color.white));

                recyclerReportDetails.setVisibility(View.VISIBLE);
                getMyZone(cabOwnerId);
            }
        });

        return view;
    }

    public void getMyZone(String cabOwnerId){

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Show MyZone Please Wait....");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppUrl.home_page, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    boolean status = jsonObject.getBoolean("status");
                    String data = jsonObject.getString("data");

                    if(status){

                        cabMyZone = new ArrayList<>();

                        JSONArray jsonArray_data = new JSONArray(data);

                        for(int i=0;i<jsonArray_data.length();i++){

                            JSONObject jsonObject_data = jsonArray_data.getJSONObject(i);

                            String job_id = jsonObject_data.getString("job_id");
                            String job_date = jsonObject_data.getString("job_date");
                            String pick_lcoation = jsonObject_data.getString("pick_lcoation ");
                            String job_type = jsonObject_data.getString("job_type");
                            String category = jsonObject_data.getString("category");

                            JSONObject jsonObject_location = new JSONObject(pick_lcoation);

                            String address = jsonObject_location.getString("address");
                            String locality = jsonObject_location.getString("locality");
                            String city = jsonObject_location.getString("city");
                            String zip = jsonObject_location.getString("zip");
                            String landmark = jsonObject_location.getString("landmark");

                            CabMyZone_ModelClass cabMyZone_modelClass = new CabMyZone_ModelClass(
                                    job_id,job_date,address,locality,city,zip,landmark,job_type,category
                            );

                            cabMyZone.add(cabMyZone_modelClass);
                        }

                        recyclerReportDetails.setVisibility(View.VISIBLE);

                        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        cabMyZoneAdapter = new CabMyZoneAdapter(getContext(), cabMyZone);
                        recyclerReportDetails.setLayoutManager(linearLayoutManager);
                        recyclerReportDetails.setHasFixedSize(true);
                        recyclerReportDetails.setAdapter(cabMyZoneAdapter);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                Toast.makeText(getActivity(), ""+error, Toast.LENGTH_SHORT).show();

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("cab_owner_id",cabOwnerId);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
}
