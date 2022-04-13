package com.in.dsdriver.fragment;

import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.in.dsdriver.R;
import com.in.dsdriver.adapter.TransactionAdapter;
import com.in.dsdriver.extra.AppUrl;
import com.in.dsdriver.modelclass.Transaction_Model;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WalletFragment extends Fragment {

    RecyclerView recyclerTransaction;
    LinearLayoutManager linearLayoutManager;
    ArrayList<Transaction_Model> tarnsaction = new ArrayList<>();
    TransactionAdapter transactionAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_wallet,container,false);
        return view;
    }

    public void getTransactionDetails(String driverId){

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

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppUrl.invoice_report, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30000,1,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.getCache().clear();
        requestQueue.add(jsonObjectRequest);

    }
}
