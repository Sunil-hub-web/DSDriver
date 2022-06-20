package com.in.dsdriver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.ProgressDialog;
import android.app.UiModeManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.in.dsdriver.driver.drivers.ForgotPassword;
import com.in.dsdriver.driver.drivers.HomeDeshbord;
import com.in.dsdriver.driver.modelclass.Login_ModelClass_Driver;
import com.in.dsdriver.extra.AppUrl;
import com.in.dsdriver.extra.SharedPrefManager_Driver;
import com.in.dsdriver.cabowner.DeshBoard;
import com.in.dsdriver.cabowner.SharedPrefManager_Owner;
import com.in.dsdriver.cabowner.modelclass.Login_ModelClass_Owner;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginPage extends AppCompatActivity {

    Button btn_signin;
    EditText edit_MobileNo,edit_Password;
    String str_UserName,str_Password,selectLogintOption;
    TextView forgotPassword;
    RadioGroup radioGroup;
    RadioButton text_Driver,text_Owner,selectedRadioButton;

    private UiModeManager uiModeManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login_page);

        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        uiModeManager = (UiModeManager) getSystemService(UI_MODE_SERVICE);
        uiModeManager.setNightMode(UiModeManager.MODE_NIGHT_NO);

        btn_signin = findViewById(R.id.btn_signin);
        edit_MobileNo = findViewById(R.id.edit_MobileNo);
        edit_Password = findViewById(R.id.edit_Password);
        forgotPassword = findViewById(R.id.forgotPassword);
        text_Driver = findViewById(R.id.text_Driver);
        text_Owner = findViewById(R.id.text_Owner);
        radioGroup = findViewById(R.id.radioGroup);

        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();

                 if (selectedRadioButtonId == -1) {

                    Toast.makeText(LoginPage.this, "select Driver Or Owner For Login", Toast.LENGTH_SHORT).show();

                }else if(edit_MobileNo.getText().toString().trim().equals("")){

                    edit_MobileNo.setError("Fill The Details");
                    edit_MobileNo.requestFocus();

                }else if(edit_MobileNo.getText().toString().trim().length()!=10){

                    edit_MobileNo.setError("Enter 10 Digite Mobile No");
                    edit_MobileNo.requestFocus();

                }else if(edit_Password.getText().toString().trim().equals("")){

                    edit_Password.setError("Fill the details");
                    edit_Password.requestFocus();

                }else{

                    selectedRadioButton = findViewById(selectedRadioButtonId);
                    selectLogintOption = selectedRadioButton.getText().toString();

                    if(selectLogintOption.equals("Driver")){

                        str_UserName = edit_MobileNo.getText().toString().trim();
                        str_Password = edit_Password.getText().toString().trim();

                        userLogin(str_UserName,str_Password);

                    }else{

                        str_UserName = edit_MobileNo.getText().toString().trim();
                        str_Password = edit_Password.getText().toString().trim();

                        Cab_Login(str_UserName,str_Password);
                    }

                }
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(selectLogintOption.equals("Driver")){

                    Intent intent = new Intent(LoginPage.this, ForgotPassword.class);
                    intent.putExtra("Driver","Driver");
                    startActivity(intent);

                }else{

                    Intent intent = new Intent(LoginPage.this, ForgotPassword.class);
                    intent.putExtra("Driver","CABOwner");
                    startActivity(intent);
                }


            }
        });


    }

    public void userLogin(String mobileNo,String password){

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Login Please Wait....");
        progressDialog.show();

        JSONObject jsonObject = new JSONObject();

        try{

            jsonObject.put("mobile",mobileNo);
            jsonObject.put("password",password);

        }catch (Exception e){

            e.printStackTrace();

        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppUrl.userLogin, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                progressDialog.dismiss();

                try {
                    String status = response.getString("status");

                    if(status.equals("true")){

                        String message = response.getString("message");

                        Toast.makeText(LoginPage.this, message, Toast.LENGTH_SHORT).show();

                        String data = response.getString("data");

                        JSONObject jsonObject_data = new JSONObject(data);

                        String user_id = jsonObject_data.getString("user_id");
                        String email = jsonObject_data.getString("email");
                        String name = jsonObject_data.getString("name");
                        String mobile = jsonObject_data.getString("mobile");
                        String status1 = jsonObject_data.getString("status");
                        String password = edit_Password.getText().toString().trim();

                        Login_ModelClass_Driver login_modelClassDriver = new Login_ModelClass_Driver(

                                user_id,name,email,mobile,status1,password
                        );

                        SharedPrefManager_Driver.getInstance(LoginPage.this).userLogin(login_modelClassDriver);

                        Intent intent = new Intent(LoginPage.this, HomeDeshbord.class);
                        startActivity(intent);


                    }else if(status.equals("false")){

                        String message = response.getString("message");

                        Toast.makeText(LoginPage.this, message, Toast.LENGTH_SHORT).show();

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
                Toast.makeText(LoginPage.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30000,1,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.getCache().clear();
        requestQueue.add(jsonObjectRequest);
    }

    public void Cab_Login(String mobileNo,String password){

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Login Please Wait....");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppUrl.cab_Login, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {

                    JSONObject jsonObject = new JSONObject(response);

                    String status = jsonObject.getString("status");

                    if(status.equals("true")){

                        String message = jsonObject.getString("message");

                        Toast.makeText(LoginPage.this, message, Toast.LENGTH_SHORT).show();

                        String data = jsonObject.getString("data");

                        JSONObject jsonObject_data = new JSONObject(data);

                        String user_id = jsonObject_data.getString("user_id");
                        String email = jsonObject_data.getString("email");
                        String name = jsonObject_data.getString("name");
                        String mobile = jsonObject_data.getString("mobile");
                        String status1 = jsonObject_data.getString("status");
                        String password = edit_Password.getText().toString().trim();

                        Login_ModelClass_Owner login_modelClass_owner = new Login_ModelClass_Owner(

                                user_id,name,email,mobile,status1,password
                        );

                        SharedPrefManager_Owner.getInstance(LoginPage.this).userLogin(login_modelClass_owner);

                        Intent intent = new Intent(LoginPage.this, DeshBoard.class);
                        startActivity(intent);

                    }else if(status.equals("false")){

                        String message = jsonObject.getString("message");

                        Toast.makeText(LoginPage.this, message, Toast.LENGTH_SHORT).show();

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
                Toast.makeText(LoginPage.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();

                params.put("mobile",mobileNo);
                params.put("password",password);

                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,2,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
        startActivity(intent);

        /*this.finish();
        System.exit(0);*/
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (SharedPrefManager_Driver.getInstance(LoginPage.this).isLoggedIn()){

            Intent intent = new Intent(LoginPage.this,HomeDeshbord.class);
            startActivity(intent);

        }else if (SharedPrefManager_Owner.getInstance(LoginPage.this).isLoggedIn()){

            Intent intent = new Intent(LoginPage.this,DeshBoard.class);
            startActivity(intent);
        }
    }

    private void setThemeDark(){
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        setContentView(R.layout.activity_splash_screen);
    }

    private void setThemeLight(){
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_splash_screen);
    }
}