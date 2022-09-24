package com.in.dsdriver.driver.fragment;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.in.dsdriver.LoginPage;
import com.in.dsdriver.R;
import com.in.dsdriver.extra.AppUrl;
import com.in.dsdriver.extra.SharedPrefManager_Driver;
import com.in.dsdriver.extra.VolleyMultipartRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    EditText text_driverName, text_driverContactNo, text_driverEmailId;

    TextView text_logout, text_edit;

    String str_driverName, str_driverContactNo, str_driverEmailId, str_driverDob, str_driverAddress,
            str_DrivingLicence, str_DariverId;

    DatePickerDialog.OnDateSetListener setListener;

    int year, month, day;
    public static final int IMAGE_CODE = 1;
    File f;
    String ImageDecode;
    private Bitmap bitmap;
    Uri imageUri, selectedImageUri;

    CircleImageView nav_profile_image;
    ActivityResultLauncher<Intent> someActivityResultLauncher;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        text_logout = view.findViewById(R.id.text_logout);
        // text_DrivingLicence = view.findViewById(R.id.text_DrivingLicence);
        text_driverName = view.findViewById(R.id.text_driverName);
        text_driverContactNo = view.findViewById(R.id.text_driverContactNo);
        text_driverEmailId = view.findViewById(R.id.text_driverEmailId);
        //text_driverDob = view.findViewById(R.id.text_driverDob);
        nav_profile_image = view.findViewById(R.id.nav_profile_image);
        //text_driverAddress = view.findViewById(R.id.text_driverAddress);
        text_edit = view.findViewById(R.id.text_edit);

        str_DariverId = SharedPrefManager_Driver.getInstance(getActivity()).getUser().getDriverID();

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                month = month + 1;
                String date = day + "/" + month + "/" + year;
                //String date = year+"-"+month+"-"+day;
                //text_driverDob.setText(date);

            }
        };

        /*text_driverDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showCalander();
            }
        });*/

        text_logout.setOnClickListener(v -> {
            //Show Your Another AlertDialog
            final Dialog dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.condition_logout);
            dialog.setCancelable(false);
            Button btn_No = dialog.findViewById(R.id.no);
            TextView textView = dialog.findViewById(R.id.editText);
            Button btn_Yes = dialog.findViewById(R.id.yes);

            btn_Yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //SharedPrefManager_Owner.getInstance(MainActivity.this).logout();

                    dialog.dismiss();
                   /* System.exit(1);
                    finish();*/

                    SharedPrefManager_Driver.getInstance(getActivity()).logout();

                    Intent intent = new Intent(getActivity(), LoginPage.class);
                    startActivity(intent);

                }
            });
            btn_No.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog.dismiss();
                }
            });

            dialog.show();
            Window window = dialog.getWindow();
            window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawableResource(R.drawable.edittextback);

        });

        getDriverDetails(str_DariverId);

        text_edit.setOnClickListener(v -> {

            if (text_edit.getText().toString().trim().equals("Edit")) {

                text_driverName.setEnabled(true);
                text_driverEmailId.setEnabled(true);
                //text_driverDob.setEnabled(true);
                //text_driverAddress.setEnabled(true);
                // text_DrivingLicence.setEnabled(true);
                //text_driverContactNo.setEnabled(false);

                text_edit.setText("Save");

            } else if (text_edit.getText().toString().trim().equals("Save")) {

                if (TextUtils.isEmpty(text_driverName.getText())) {

                    text_driverName.setError("Fill The Details");

                } else if (TextUtils.isEmpty(text_driverEmailId.getText())) {

                    text_driverEmailId.setError("Fill The Details");

                } /*else if (TextUtils.isEmpty(text_driverDob.getText())) {

                    text_driverDob.setError("Fill The Details");

                }*//*else if(TextUtils.isEmpty(text_driverAddress.getText())){

                    text_driverAddress.setError("Fill The Details");

                }else if(TextUtils.isEmpty(text_DrivingLicence.getText())){

                    text_DrivingLicence.setError("Fill The Details");

                }*/ else {

                    str_driverName = text_driverName.getText().toString().trim();
                    str_driverEmailId = text_driverEmailId.getText().toString().trim();
                    //str_driverDob = text_driverDob.getText().toString().trim();
                    // str_driverAddress = text_driverAddress.getText().toString().trim();
                    //str_DrivingLicence = text_DrivingLicence.getText().toString().trim();

                    updateDetails(str_DariverId, str_driverName, str_driverEmailId, str_driverDob);


                }

            }

        });

        nav_profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showFileChooser();
            }
        });

        someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {


                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // There are no request codes
                            Intent data = result.getData();

                            if(data != null){

                                imageUri = data.getData();

                                try {

                                    //bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), intent.getData());

                                    InputStream imageStream = getContext().getContentResolver().openInputStream(imageUri);
                                    bitmap = BitmapFactory.decodeStream(imageStream);
                                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                                    nav_profile_image.setImageBitmap(bitmap);

                                    if (imageUri.equals("")) {

                                        Toast.makeText(getActivity(), "Select Image", Toast.LENGTH_SHORT).show();

                                    } else {

                                        String driverId = SharedPrefManager_Driver.getInstance(getContext()).getUser().getDriverID();

                                        profileImageUpload(bitmap, driverId);
                                    }


                                } catch (IOException e) {
                                    e.printStackTrace();

                                    Toast.makeText(getActivity(), "Please try again", Toast.LENGTH_LONG)
                                            .show();
                                }
                            }

                        }

                    }
                });

        return view;
    }

    public void getDriverDetails(String driverId) {

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please Wait....");
        progressDialog.setTitle("Get ProfileDetails");
        progressDialog.show();

        Map<String, String> params = new HashMap<>();

        try {

            params.put("driver_id", driverId);

        } catch (Exception e) {

            e.printStackTrace();
        }

        JSONObject jsonObject = new JSONObject(params);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppUrl.getDriverProfile, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                progressDialog.dismiss();

                try {
                    String status = response.getString("status");

                    if (status.equals("true")) {

                        String driver_id = response.getString("driver_id");
                        String name = response.getString("name");
                        String email = response.getString("email");
                        String mobile = response.getString("mobile");
                        String dob = response.getString("dob");
                        String dl_no = response.getString("dl_no");
                        String adresss = response.getString("adresss");


                        SimpleDateFormat fromUser = new SimpleDateFormat("yyyy/MM/dd");
                        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");

                        try {

                            Date dateFromUser = fromUser.parse(dob); // Parse it to the exisitng date pattern and return Date type
                            String dateMyFormat = myFormat.format(dateFromUser); // format it to the date pattern you prefer
                            //text_driverDob.setText(dateMyFormat);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        text_driverName.setText(name);
                        text_driverEmailId.setText(email);

                        //text_driverAddress.setText(adresss);
                        //text_DrivingLicence.setText(dl_no);
                        text_driverContactNo.setText(mobile);

                        text_driverName.setEnabled(false);
                        text_driverEmailId.setEnabled(false);
                        //text_driverDob.setEnabled(false);
                        //text_driverAddress.setEnabled(false);
                        //text_DrivingLicence.setEnabled(false);
                        text_driverContactNo.setEnabled(false);


                    } else if (status.equalsIgnoreCase("false")) {

                        String message = response.getString("message");
                        Toast.makeText(getActivity(), "message", Toast.LENGTH_SHORT).show();

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
        requestQueue.add(jsonObjectRequest);


    }

    public void updateDetails(String driver_id, String driverName, String driverEmail, String driverDOB) {

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please Wait....");
        progressDialog.setTitle("Update ProfileDetails");
        progressDialog.show();

        Map<String, String> params = new HashMap<>();

        try {

            params.put("driver_id", driver_id);
            params.put("name", driverName);
            params.put("email", driverEmail);
            params.put("dob", driverDOB);
            params.put("licence_no", "");
            params.put("address", "");

        } catch (Exception e) {

            e.printStackTrace();
        }

        JSONObject jsonObject = new JSONObject(params);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppUrl.updateDriverProfile, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                progressDialog.dismiss();

                try {
                    String status = response.getString("status");

                    if (status.equals("true")) {

                        String message = response.getString("message");
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

                        String driver_id = response.getString("driver_id");
                        String name = response.getString("name");
                        String email = response.getString("email");
                        String mobile = response.getString("mobile");
                        String dob = response.getString("dob");
                        String dl_no = response.getString("dl_no");
                        String adresss = response.getString("adresss");

                        SimpleDateFormat fromUser = new SimpleDateFormat("yyyy/MM/dd");
                        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");

                        try {

                            Date dateFromUser = fromUser.parse(dob); // Parse it to the exisitng date pattern and return Date type
                            String dateMyFormat = myFormat.format(dateFromUser); // format it to the date pattern you prefer
                            //text_driverDob.setText(dateMyFormat);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        text_driverName.setText(name);
                        text_driverEmailId.setText(email);
                        //text_driverDob.setText(dob);
                        //text_driverAddress.setText(adresss);
                        //text_DrivingLicence.setText(dl_no);
                        text_driverContactNo.setText(mobile);

                        text_driverName.setEnabled(false);
                        text_driverEmailId.setEnabled(false);
                        //text_driverDob.setEnabled(false);
                        // text_driverAddress.setEnabled(false);
                        // text_DrivingLicence.setEnabled(false);
                        text_driverContactNo.setEnabled(false);

                        text_edit.setText("Edit");

                    } else if (status.equalsIgnoreCase("false")) {

                        String message = response.getString("message");
                        Toast.makeText(getActivity(), "message", Toast.LENGTH_SHORT).show();

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
        requestQueue.add(jsonObjectRequest);
    }


    public void showCalander() {

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getActivity(), android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                setListener, year, month, day);

        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.show();

    }

    public void showCalender1() {

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                month = month + 1;
                String date = day + "/" + month + "/" + year;
                //String date = year+"-"+month+"-"+day;
                //text_driverDob.setText(date);
            }
        }, year, month, day);

        datePickerDialog.show();
    }

    public void showFileChooser() {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        someActivityResultLauncher.launch(Intent.createChooser(intent, "title"));
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

/*    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {

            if (requestCode == IMAGE_CODE && resultCode == Activity.RESULT_OK &&
                    data != null && data.getData() != null) {

                imageUri = data.getData();
                //profile_image.setImageURI(imageUri);

                String[] FILE = {MediaStore.Images.Media.DATA};


                Cursor cursor = getActivity().getContentResolver().query(imageUri,
                        FILE, null, null, null);

                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(FILE[0]);
                ImageDecode = cursor.getString(columnIndex);
                f = new File(ImageDecode);
                selectedImageUri = Uri.fromFile(f);
                Log.d("selectedImageUri", selectedImageUri.toString());
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImageUri);
                //profile_image.setImageBitmap(bitmap);
                //profile_image.setImageURI(selectedImageUri);

                Log.d("ImageDecode", ImageDecode);
                Log.d("ImageDecode1", f.toString());
                Log.d("ImageDecode2", selectedImageUri.toString());

                cursor.close();

                if (selectedImageUri.equals("")) {

                    Toast.makeText(getActivity(), "Select Image", Toast.LENGTH_SHORT).show();

                } else {

                    String driverId = SharedPrefManager_Driver.getInstance(getContext()).getUser().getDriverID();

                    profileImageUpload(bitmap, driverId);
                }
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Please try again", Toast.LENGTH_LONG)
                    .show();
        }
    }*/

    public void profileImageUpload(Bitmap bitmap, String driverId) {

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Profile Pic Upload Please wait...");
        progressDialog.show();

        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, AppUrl.profileImageUpload, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(new String(response.data));

                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");

                    if (status.equals("true")) {

                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

                    } else {

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
                Toast.makeText(getActivity(), "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                params.put("driver_id", driverId);

                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() throws AuthFailureError {

                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("image", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));

                return params;
            }
        };

        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(volleyMultipartRequest);
    }
}