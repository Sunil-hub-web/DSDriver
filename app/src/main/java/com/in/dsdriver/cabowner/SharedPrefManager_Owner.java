package com.in.dsdriver.cabowner;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.in.dsdriver.LoginPage;
import com.in.dsdriver.cabowner.modelclass.Login_ModelClass_Owner;


public class SharedPrefManager_Owner {

    private static final String SHARED_PREF_NAME1 = "simplifiedcodingsharedpref1";
    private static final String KEY_email1 = "keylast_name1";
    private static final String KEY_mobile_number1 = "keymobile_number1";
    private static final String KEY_ID1 = "keyid1";
    private static final String KEY_password1 = "keypassword1";
    private static final String KEY_Name1 = "keyname1";
    private static final String KEY_STATUES1 = "statues1";
    private static SharedPrefManager_Owner mInstance1;
    private static Context mCtx1;

    public SharedPrefManager_Owner(Context context) {
        mCtx1 = context;
    }

    public static SharedPrefManager_Owner getInstance(Context context) {
        if (mInstance1 == null) {
            mInstance1 = new SharedPrefManager_Owner(context);
        }
        return mInstance1;
    }

    //method to let the user register
    //this method will store the user data in shared preferences
    public void userLogin(Login_ModelClass_Owner login_modelClass_owner) {

        SharedPreferences sharedPrefManager = mCtx1.getSharedPreferences(SHARED_PREF_NAME1, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefManager.edit();


        editor.putString(KEY_ID1,         login_modelClass_owner.getCabOwnerId());
        editor.putString(KEY_Name1,     login_modelClass_owner.getName ());
        editor.putString(KEY_email1,                login_modelClass_owner.getEmail ());
        editor.putString(KEY_mobile_number1,                login_modelClass_owner.getMobileno ());
        editor.putString(KEY_STATUES1,                login_modelClass_owner.getStatues ());
        editor.putString(KEY_password1,                login_modelClass_owner.getPassword ());



        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPrefManager = mCtx1.getSharedPreferences(SHARED_PREF_NAME1, Context.MODE_PRIVATE);
        return sharedPrefManager.getString(KEY_mobile_number1, null) != null;
    }

    //this method will give the logged in user
    public Login_ModelClass_Owner getUser() {
        SharedPreferences sharedPrefManager = mCtx1.getSharedPreferences(SHARED_PREF_NAME1, Context.MODE_PRIVATE);
        return new Login_ModelClass_Owner(


                sharedPrefManager.getString(KEY_ID1, null),
                sharedPrefManager.getString(KEY_Name1, null),
                sharedPrefManager.getString(KEY_email1, null),
                sharedPrefManager.getString(KEY_mobile_number1, null),
                sharedPrefManager.getString(KEY_STATUES1, null),
                sharedPrefManager.getString(KEY_password1, null)

        );

    }

    //this method will logout the user
    public void logout() {

        SharedPreferences sharedPrefManager = mCtx1.getSharedPreferences(SHARED_PREF_NAME1, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefManager.edit();
        editor.clear();
        editor.apply();
        mCtx1.startActivity(new Intent (mCtx1, LoginPage.class));
    }

}
