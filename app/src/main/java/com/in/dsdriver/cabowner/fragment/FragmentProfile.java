package com.in.dsdriver.cabowner.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.in.dsdriver.LoginPage;
import com.in.dsdriver.R;
import com.in.dsdriver.cabowner.SharedPrefManager_Owner;

import java.util.Calendar;

public class FragmentProfile extends Fragment {

    TextView text_logout,text_edit;
    DatePickerDialog.OnDateSetListener setListener;
    int year,month,day;
    EditText text_driverName,text_driverContactNo,text_driverEmailId,text_driverDob,text_driverAddress,text_DrivingLicence;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.profile_fargment,container,false);

        text_logout = view.findViewById(R.id.text_logout);
        text_DrivingLicence = view.findViewById(R.id.text_DrivingLicence);
        text_driverName = view.findViewById(R.id.text_driverName);
        text_driverContactNo = view.findViewById(R.id.text_driverContactNo);
        text_driverEmailId = view.findViewById(R.id.text_driverEmailId);
        text_driverDob = view.findViewById(R.id.text_driverDob);
        text_driverAddress = view.findViewById(R.id.text_driverAddress);
        text_edit = view.findViewById(R.id.text_edit);

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                month = month+1;
                String date = day+"/"+month+"/"+year;
                //String date = year+"-"+month+"-"+day;
                text_driverDob.setText(date);

            }
        };

        text_driverDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showCalender1();
            }
        });

        text_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

                        SharedPrefManager_Owner.getInstance(getActivity()).logout();
                        //SharedPrefManager_Driver.getInstance(getActivity()).logout();

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

            }
        });

        text_edit.setOnClickListener(v -> {

            if (text_edit.getText().toString().trim().equals("Edit")){

                text_driverName.setEnabled(true);
                text_driverEmailId.setEnabled(true);
                text_driverDob.setEnabled(true);
                text_driverAddress.setEnabled(true);
                text_DrivingLicence.setEnabled(true);
                //text_driverContactNo.setEnabled(false);

                text_edit.setText("Save");

            }else if(text_edit.getText().toString().trim().equals("Save")){

                if(TextUtils.isEmpty(text_driverName.getText())){

                    text_driverName.setError("Fill The Details");

                }else if(TextUtils.isEmpty(text_driverEmailId.getText())){

                    text_driverEmailId.setError("Fill The Details");

                }else if(TextUtils.isEmpty(text_driverDob.getText())){

                    text_driverDob.setError("Fill The Details");

                }else if(TextUtils.isEmpty(text_driverAddress.getText())){

                    text_driverAddress.setError("Fill The Details");

                }else if(TextUtils.isEmpty(text_DrivingLicence.getText())){

                    text_DrivingLicence.setError("Fill The Details");

                }else{

                   /* str_driverName = text_driverName.getText().toString().trim();
                    str_driverEmailId = text_driverEmailId.getText().toString().trim();
                    str_driverDob = text_driverDob.getText().toString().trim();
                    str_driverAddress = text_driverAddress.getText().toString().trim();
                    str_DrivingLicence = text_DrivingLicence.getText().toString().trim();

                    updateDetails(str_DariverId,str_driverName,str_driverEmailId,str_driverDob,str_DrivingLicence,str_driverAddress);*/


                }

            }

        });

        return view;
    }

    public void showCalender1(){

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                month = month+1;
                String date = day+"/"+month+"/"+year;
                //String date = year+"-"+month+"-"+day;
                text_driverDob.setText(date);
            }
        },year,month,day);

        datePickerDialog.show();
    }
}
