package com.in.dsdriver.driver.fragment;

import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
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
import com.in.dsdriver.driver.adapter.TransactionAdapter;
import com.in.dsdriver.extra.AppUrl;
import com.in.dsdriver.extra.SharedPrefManager_Driver;
import com.in.dsdriver.driver.modelclass.Transaction_Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class WalletFragment extends Fragment {

    RecyclerView recyclerTransaction;
    LinearLayoutManager linearLayoutManager;
    ArrayList<Transaction_Model> tarnsaction = new ArrayList<>();
    TransactionAdapter transactionAdapter;
    TextView text_price;
    String driverId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_wallet,container,false);

        recyclerTransaction = view.findViewById(R.id.recyclerTransaction);
        text_price = view.findViewById(R.id.text_price);

        driverId = SharedPrefManager_Driver.getInstance(getActivity()).getUser().getDriverID();

        getTransactionDetails(driverId);

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

                progressDialog.dismiss();

                try {
                    String status = response.getString("status");
                    String driver_balance = response.getString("driver_balance");
                    String allbooking = response.getString("transactions");

                    text_price.setText("Rs  "+ driver_balance);

                    JSONArray jsonArray_booking = new JSONArray(allbooking);

                    for (int i=0;i<jsonArray_booking.length();i++){

                        JSONObject jsonObject_booking = jsonArray_booking.getJSONObject(i);

                        String invoice_id = jsonObject_booking.getString("invoice_id");
                        String amount = jsonObject_booking.getString("amount");
                        String tr_date = jsonObject_booking.getString("tr_date");

                       /* String booking_idss = jsonObject_booking.getString("booking_idss");
                        String payable_amount = jsonObject_booking.getString("payable_amount");
                        String invoice_date = jsonObject_booking.getString("invoice_date");
                        String deosite_amount = jsonObject_booking.getString("deosite_amount");
                        String amount = jsonObject_booking.getString("amount");
                        String gst_amount = jsonObject_booking.getString("gst_amount");
                        String invoice_amount = jsonObject_booking.getString("invoice_amount");
                        String balance_deposite = jsonObject_booking.getString("balance_deposite");*/

                        Transaction_Model transaction_model = new Transaction_Model(invoice_id,tr_date,amount,"View");
                        tarnsaction.add(transaction_model);

                    }

                    linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
                    transactionAdapter = new TransactionAdapter(tarnsaction,getActivity());
                    recyclerTransaction.setLayoutManager(linearLayoutManager);
                    recyclerTransaction.setHasFixedSize(true);
                    recyclerTransaction.setAdapter(transactionAdapter);


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
