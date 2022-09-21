package com.in.dsdriver.extra;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.in.dsdriver.LoginPage;
import com.in.dsdriver.driver.modelclass.Login_ModelClass_Driver;


public class SharedPrefManager_Driver {

    private static final String SHARED_PREF_NAME = "simplifiedcodingsharedpref";
    private static final String KEY_email = "keylast_name";
    private static final String KEY_mobile_number = "keymobile_number";
    private static final String KEY_ID = "keyid";
    private static final String KEY_password = "keypassword";
    private static final String KEY_Name = "keyname";
    private static final String KEY_STATUES = "statues";
    private static final String KEY_shifttype = "shift_type";
    private static final String KEY_driver_type = "driver_type";
    private static SharedPrefManager_Driver mInstance;
    private static Context mCtx;

    public SharedPrefManager_Driver(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager_Driver getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager_Driver(context);
        }
        return mInstance;
    }

    //method to let the user register
    //this method will store the user data in shared preferences
    public void userLogin(Login_ModelClass_Driver login_modelClassDriver) {

        SharedPreferences sharedPrefManager = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefManager.edit();


        editor.putString(KEY_ID,         login_modelClassDriver.getDriverID ());
        editor.putString(KEY_Name,     login_modelClassDriver.getName ());
        editor.putString(KEY_email,                login_modelClassDriver.getEmail ());
        editor.putString(KEY_mobile_number,                login_modelClassDriver.getMobileno ());
        editor.putString(KEY_STATUES,                login_modelClassDriver.getStatues ());
        editor.putString(KEY_password,                login_modelClassDriver.getPassword ());
        editor.putString(KEY_driver_type,                login_modelClassDriver.getDriver_type ());
        editor.putString(KEY_shifttype,                login_modelClassDriver.getShift_type ());



        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPrefManager = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPrefManager.getString(KEY_mobile_number, null) != null;
    }

    //this method will give the logged in user
    public Login_ModelClass_Driver getUser() {
        SharedPreferences sharedPrefManager = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new Login_ModelClass_Driver(


                sharedPrefManager.getString(KEY_ID, null),
                sharedPrefManager.getString(KEY_Name, null),
                sharedPrefManager.getString(KEY_email, null),
                sharedPrefManager.getString(KEY_mobile_number, null),
                sharedPrefManager.getString(KEY_STATUES, null),
                sharedPrefManager.getString(KEY_password, null),
                sharedPrefManager.getString(KEY_driver_type, null),
                sharedPrefManager.getString(KEY_shifttype, null)

        );

    }

    //this method will logout the user
    public void logout() {

        SharedPreferences sharedPrefManager = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefManager.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent (mCtx, LoginPage.class));
    }

}
