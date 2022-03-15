package com.example.driversuvidha;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.driversuvidha.extra.AppUrl;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import in.aabhasjindal.otptextview.OtpTextView;

public class ForgotPassword extends AppCompatActivity {

    EditText edit_MobileNo;
    OtpTextView otp_view;
    Button btn_submit;
    RelativeLayout rel_otp;
    String driver_id;
    TextInputLayout edit_MobileNo1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edit_MobileNo = findViewById(R.id.edit_MobileNo);
        otp_view = findViewById(R.id.otp_view);
        btn_submit = findViewById(R.id.btn_submit);
        rel_otp = findViewById(R.id.rel_otp);
        edit_MobileNo1 = findViewById(R.id.edit_MobileNo1);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(btn_submit.getText().toString().trim().equals("Send OTP")){

                    if(TextUtils.isEmpty(edit_MobileNo.getText())){

                        edit_MobileNo.setError("Fill the Details");
                        edit_MobileNo.requestFocus();

                    }else if(edit_MobileNo.getText().toString().trim().length() != 10){

                        edit_MobileNo.setError("Fill the Details");
                        edit_MobileNo.requestFocus();

                    }else{

                        String str_mobileNo = edit_MobileNo.getText().toString().trim();

                        sendOtp(str_mobileNo);
                    }

                }else {

                    if(otp_view.getOTP().length() != 6){

                        Toast.makeText(ForgotPassword.this, "Enter 6 Digits OTP", Toast.LENGTH_SHORT).show();

                    }else{

                        String otp = otp_view.getOTP();
                        verifayOTP(driver_id,otp);
                    }

                }
            }
        });


    }

    public void sendOtp(String mobileNo){

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Send OTP Please Wait....");
        progressDialog.show();

        JSONObject jsonObject = new JSONObject();

        try{

            jsonObject.put("mobile",mobileNo);

        }catch(Exception e){

            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppUrl.forgetpassword, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                progressDialog.dismiss();

                try {
                    String status = response.getString("status");

                    if(status.equals("success")){

                        Toast.makeText(ForgotPassword.this, "OTP send Your Mobile No", Toast.LENGTH_SHORT).show();

                        driver_id = response.getString("driver_id");
                        String otp = response.getString("otp");

                        rel_otp.setVisibility(View.VISIBLE);
                        edit_MobileNo.setVisibility(View.GONE);
                        edit_MobileNo1.setVisibility(View.GONE);

                        btn_submit.setText("Verifay Otp");

                    }else if(status.equals("false")){

                        String message = response.getString("message");
                        Toast.makeText(ForgotPassword.this, message, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(ForgotPassword.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30000,1,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.getCache().clear();
        requestQueue.add(jsonObjectRequest);

    }

    public void verifayOTP(String driverId,String OTP){

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Send OTP Please Wait....");
        progressDialog.show();

        JSONObject jsonObject = new JSONObject();

        try{

            jsonObject.put("otp",OTP);
            jsonObject.put("driver_id",driverId);

        }catch (Exception e){

            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppUrl.verifyOtp, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                progressDialog.dismiss();

                try {
                    String status = response.getString("status");

                    if(status.equals("success")){

                        Toast.makeText(ForgotPassword.this, "OTP Verifayed Successfully", Toast.LENGTH_SHORT).show();

                        String driver_id = response.getString("driver_id");

                        Intent intent = new Intent(ForgotPassword.this,ChangePasssword.class);
                        intent.putExtra("driver_id",driver_id);
                        startActivity(intent);

                    }else if(status.equals("false")){

                        String message = response.getString("message");

                        Toast.makeText(ForgotPassword.this, message, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(ForgotPassword.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30000,1,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.getCache().clear();
        requestQueue.add(jsonObjectRequest);

    }
}